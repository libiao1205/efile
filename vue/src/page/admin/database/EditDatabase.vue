<template>
    <div class="edit-database">
        <el-dialog :title="dialogTitle" :visible.sync="visible" :close-on-click-modal="false" width="460px" @close="onClose">
            <el-form ref="form" :model="form" :rules="rules" label-width="110px" :label-position="'left'">
                <el-form-item label="数据源链接别名" prop="aliasName">
                    <el-input v-model="form.aliasName"></el-input>
                </el-form-item>
                <el-form-item label="数据库名称" prop="dbName">
                    <el-input v-model="form.dbName"></el-input>
                </el-form-item>
                <el-form-item label="数据驱动" prop="driverName">
                    <el-select v-model="form.driverName" placeholder="请选择数据驱动">
                        <el-option v-for="(item, index) in dbDrivers" :key="index" :label="item.comment" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="IP" prop="ip">
                    <el-input v-model="form.ip"></el-input>
                </el-form-item>
                <el-form-item label="端口号" prop="port">
                    <el-input v-model.number="form.port"></el-input>
                </el-form-item>
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="form.password" type="password" v-if="username != 'Root'"></el-input>
                    <el-input v-model="form.password" v-if="username == 'Root'"></el-input>
                </el-form-item>
                <!-- <el-form-item label="说明" prop="comment">
                    <el-input type="textarea" v-model="form.connectText"></el-input>
                </el-form-item> -->
                <el-form-item label="启用" prop="status">
                    <el-switch
                        v-model="form.status"
                        active-text="是"
                        inactive-text="否"
                    ></el-switch>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-add-new" @click="dialogOnTest">
                    <span style="padding-left: 10px;"><b>测试连接</b></span>
                </el-button>
                <el-button type="primary" icon="iconfont icon-add-new" @click="dialogOnSubmit">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="dialogOnClose">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import Utils from '../../../util/utils';
import {mapState} from "vuex";
export default {
    name: 'EditDatabase',
    data() {
        const portRule = (rule, value, callback) => {
            if (value > 0 && value <= 65535) {
                callback();
            } else {
                callback(new Error('请输入正确的端口号范围(1-65535)'));
            }
        };

        return {
            visible: false,
            dialogType: 'create',
            form: {
                dbName: '',
                aliasName: '',
                driverName: '',
                ip: '',
                port: '',
                username: '',
                password: '',
                status: true,
                // comment: '',
            },
            rules: {
                dbName: [
                    {
                        required: true,
                        message: '请输入数据库名称',
                        trigger: 'blur',
                    }
                ],
                driverName: [
                    {
                        required: true,
                        message: '请选择数据驱动',
                        trigger: 'change',
                    },
                ],
                ip: [
                    {
                        required: true,
                        message: '请输入IP',
                        trigger: 'blur',
                    },
                    {
                        required: true,
                        pattern:  /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)|localhost$/,
                        message: '请输入有效的IP',
                        trigger: 'blur',
                    },
                ],
                port: [
                    {
                        required: true,
                        message: '请输入端口号',
                        trigger: 'blur',
                    },
                    {
                        type: 'number',
                        message: '端口号必须为数字值',
                        trigger: 'blur',
                    },
                    {
                        validator: portRule,
                        trigger: 'blur',
                    },
                ],
                username: [
                    {
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur',
                    },
                ],
                password: [
                    {
                        required: true,
                        message: '请输入密码',
                        trigger: 'blur',
                    },
                ],
            },
            dbDrivers: [],
        };
    },
    computed: {
        dialogTitle() {
            if (this.dialogType === 'edit') {
                return '修改数据库';
            }
            return '新增数据库';
        },
        ...mapState({
            username: state => state.user.name,
        })
    },
    mounted() {
        this.initDbDrivers();
    },
    methods: {
        onClose() {
            this.initForm();
            this.$refs['form'].resetFields();
        },
        initDbDrivers() {
            this.$axios({
                method: 'get',
                url: `/external/dbDriver`,
            }).then((res) => {
                if (res.c === 0) {
                    this.dbDrivers = res.ctt;
                }
            });
        },
        initForm() {
            this.form  = {
                id: '',
                aliasName:'',
                dbName: '',
                driverName: '',
                ip: '',
                port: '',
                username: '',
                password: '',
                status: true,
            };
        },
        dialogOnTest() {
            const {id, ...formItem} = this.form;
            const data = {
                ...formItem,
                status: this.form.status ? 1 : 0,
                port: String(this.form.port),
            };
            // console.log(data);
            // return;
            this.$axios({
                method: 'post',
                url: `/external/db/test`,
                data,
            }).then((res) => {
                if (res.c === 0) {
                    this.$message.success('测试连接成功');
                } else {
                    this.$message.warning(res.msg);
                }
                // this.dialogOnClose();
                // Utils.$emit('refreshDb');
            }).catch((err) => {
                this.$message.error(`测试连接数据失败，失败码：${err.response.status}`);
            });
        },
        dialogOnClose() {
            this.visible = false;
            this.initForm();
            this.$refs['form'].resetFields();
        },
        dialogOnSubmit() {
            this.$refs['form'].validate((valid) => {
                if (!valid) {
                    return;
                }
                if (this.dialogType === 'create') {
                    const {id, status, port, ...formItem} = this.form;
                    const data = {
                        ...formItem,
                        status: status ? 1 : 0,
                        port: String(port),
                    };
                    this.$axios({
                        method: 'post',
                        url: `/external/db`,
                        data,
                    }).then((res) => {
                        this.$message.success('新建成功');
                        this.dialogOnClose();
                        Utils.$emit('refreshDb');
                    }).catch((err) => {
                        this.$message.error(`新建数据失败，失败码：${err.response.status}`);
                    });
                }
                if (this.dialogType === 'edit') {
                    const data = {
                        ...this.form,
                        status: this.form.status ? 1 : 0,
                        port: String(this.form.port),
                    };
                    this.$axios({
                        method: 'put',
                        url: `/external/dbUpdate`,
                        data,
                    }).then((res) => {
                        this.$message.success('修改成功');
                        this.dialogOnClose();
                        Utils.$emit('refreshDb');
                    }).catch((err) => {
                        this.$message.error(`修改数据失败，失败码：${err.response.status}`);
                    });
                }
            });
        },
    },
}
</script>

<style lang="scss">
// .edit-database {

// }
</style>
