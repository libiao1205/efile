<template>
    <div class="sync-database">
        <el-dialog :title="dialogTitle" :visible.sync="visible" :close-on-click-modal="false" width="460px">
            <el-form ref="form" :model="form" label-width="100px" :label-position="'left'">
                <el-form-item label="源据数名称" prop="name">
                    <el-input v-model="form.name" readonly></el-input>
                </el-form-item>
                <el-form-item label="上次同步时间" prop="dateTime">
                    <el-input v-model="form.dateTime" readonly></el-input>
                </el-form-item>
                <el-form-item label="上次同步状态" prop="status">
                    <el-input v-model="form.status" readonly></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-add-new" @click="dialogOnSubmit">
                    <span style="padding-left: 10px;"><b>开始同步</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="dialogOnClose">
                    <span style="padding-left: 10px;"><b>取消</b></span>
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import Utils from '../../../util/utils';
export default {
    name: 'SyncDatabase',
    data() {
        return {
            visible: false,
            form: {
                id: '',
                name: '',
                dateTime: '',
                status: '',
            },
            dbDrives: [],
        };
    },
    computed: {
        dialogTitle() {
            return '';
        }
    },
    methods: {
        dialogOnClose() {
            this.visible = false;
            this.$refs['form'].resetFields();
        },
        dialogOnSubmit() {
            this.$axios({
                method: 'get',
                url: `/external/db/${this.form.id}/input`,
            }).then((res) => {
                if (res.c === 0) {
                    this.$message.success('同步成功');
                    Utils.$emit('refreshDb');
                } else {
                    this.$message.warning(res.msg);
                }
                this.dialogOnClose();
            }).catch((err) => {
                this.$message.error(`同步数据失败，失败码：${err.response.status}`);
            });
        },
    },
}
</script>

<style lang="scss">
// .sync-database {

// }
</style>
