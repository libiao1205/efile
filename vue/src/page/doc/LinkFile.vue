<template>
    <div>
        <!--外链界面-->
        <el-dialog :title="formLink.title" :visible.sync="formLink.visible" :close-on-click-modal="true" width="800px">
            <el-form :model="formLink.data" label-width="80px" :rules="formLink.rules" ref="formLinkData">
                <el-row>
                    <el-form-item label="外部链接" prop="fullUrl">
                        <el-col :span=24><el-input v-model="formLink.data.fullUrl" :disabled="true">
                            <template slot="prepend">https://</template>
                            <el-button slot="append" icon="el-icon-refresh" @click="handleResetRandom()">重新生成</el-button>
                        </el-input></el-col>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-col :span=12>
                        <el-form-item label="访问密码" prop="password">
                            <el-input id="success_form_input" v-model="formLink.data.password" auto-complete="off">
                                <el-button slot="append" icon="el-icon-refresh" @click="handleResetPassword()">自动生成</el-button>
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" :offset="8">
                        <el-button ref="copy" class="btnCopy" type="primary" style="float: right" icon="iconfont icon-wenjian"
                                   @click="handleCopyClipboard()" plain>
                            拷贝到剪贴板
                        </el-button>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span=7>
                        <el-form-item label="截至日期">
                            <el-date-picker v-model="formLink.data.endDate" type="date" size="small" placeholder="选择外链截至日期"
                                format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" :picker-options="formLink.pickerOptions">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click.native="handleLinkSubmit" :loading="formLink.loading">开始共享</el-button>
                <el-button @click.native="formLink.visible = false">关闭</el-button>
            </div>
        </el-dialog>
        <!--外链界面-->
    </div>
</template>

<script>
    export default {
        name: "LinkFile",
        data() {
            return {
                copyBtn : null,
                linkUrlHead:{
                    head:this.$linkUrlHead,
                },
                props: {
                    linked: {
                        type: Function,
                        default: null
                    },
                },
                /*外链数据处理 Start*/
                formLink: {
                    title:'文档共享',
                    data:{
                        randomFolderName:'',
                        fullUrl:'',
                        password: '',
                        days:'',
                        endDate:'',
                        resources:[],
                    },
                    rules: {
                        password: [
                            {required: true, message: '请输入密码', trigger: 'blur'}
                        ],
                    },
                    pickerOptions: {
                        disabledDate(time) {
                            return time.getTime() < Date.now() - 8.64e7;
                        }
                    },
                    visible: false,
                    loading: false,
                },
                /*外链数据处理 End*/
            }
        },
        methods:{
            //文件外链界面
            handleAddLink (multipleSelection) {
                if (multipleSelection.length === 0){
                    this.$message.error("没有选择需要外链的文件或者文件夹，请重新选择！")
                    return
                }
                this.formLink.visible = true
                let randomFolderName = this.randomString(24)
                this.formLink.data = {
                    id: 0 ,
                    randomFolderName:randomFolderName,
                    password:this.randomString(6),
                    fullUrl: `${this.linkUrlHead.head}/link/${randomFolderName}/`,
                    resources: new Array()
                }

                for ( let i = 0; i <multipleSelection.length; i++){
                    let res = {
                        resourceId: multipleSelection[i].id,
                        kind: multipleSelection[i].kind,
                        name:multipleSelection[i].name,
                        suffix:multipleSelection[i].suffix,
                    };
                    this.formLink.data.resources.push(res);
                    console.log(multipleSelection[i].kind);
                }
                if (this.$refs.formLinkData) this.$refs.formLinkData.resetFields()
            },
            //重新生成外链链接
            handleResetRandom(){
                this.formLink.data.randomFolderName = this.randomString(24)
                this.formLink.data.fullUrl = `${this.linkUrlHead.head}/share/${this.formLink.data.randomFolderName}/`
            },
            //重新设置密码
            handleResetPassword(){
                this.formLink.data.password = this.randomString(6)
            },
            //外链信息复制
            handleCopyClipboard() {
                let text = '外链地址:' + this.formLink.data.fullUrl + ' 密码:' + this.formLink.data.password
                this.copyBtn = new this.$clipboard(
                    '.btnCopy',{
                        text: function() {
                            return text;
                        }
                    }
                );
                let _this = this;
                let clipboard = _this.copyBtn;
                clipboard.on('success', function() {
                    _this.$message({
                        message: '已复制外链信息到剪贴板',
                        type: 'success'
                    });
                });
                clipboard.on('error', function() {
                    _this.$message({
                        message: '复制失败，请手动选择复制',
                        type: 'error'
                    });
                });
            },
            //文件外链
            handleLinkSubmit(){
                this.$refs.formLinkData.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formLink.loading = true
                            let para = Object.assign({}, this.formLink.data);
                            this.$axios({
                                url: '/user/link',
                                method: 'post',
                                data: para
                            }).then((res) => {
                                this.$message({
                                    message: '提交成功',
                                    type: 'success'
                                });
                                this.$refs.formLinkData.resetFields()
                                this.formLink.loading = false
                                this.formLink.visible = false
                                this.linked();
                            }).catch(() => {
                            });
                        }).catch(() => {
                        });
                    }
                })
            },
            //生成随机字符串，默认24位长度
            randomString(len) {
                len = len || 24;
                // var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
                var $chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
                var maxPos = $chars.length;
                var pwd = '';
                for (var i = 0; i < len; i++) {
                    pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
                }
                return pwd;
            },
        }
    }
</script>


<style lang="scss">

</style>
