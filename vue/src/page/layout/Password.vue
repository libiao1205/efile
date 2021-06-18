<template>
    <el-dialog title="修改密码" :visible.sync="formPassword.visible" :close-on-click-modal="true" width="500px">
        <el-form :model="formPassword.data" label-width="120px" :rules="formPassword.rules" ref="formPassword">
            <el-form-item label="旧密码" prop="oldPassword">
                <el-input v-model="formPassword.data.oldPassword" type="password" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
                <el-input v-model="formPassword.data.newPassword"  type="password" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="重新输入密码" prop="repeatPassword">
                <el-input v-model="formPassword.data.repeatPassword"  type="password" auto-complete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click.native="handlePasswordSubmit" :loading="formPassword.loading">确定</el-button>
            <el-button @click.native="formPassword.visible = false">取消</el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "Password",
        data(){
            return{
                formPassword:{
                    data:{
                        oldPassword:'',
                        newPassword:'',
                        repeatPassword:'',
                    },
                    visible: false,
                    loading: false,
                    rules: {
                        oldPassword: [
                            {required: true, message: '请输入旧密码', trigger: 'blur'},
                        ],
                        newPassword: [
                            {required: true, message: '请输入旧密码', trigger: 'blur'},
                            { min: 6, message: '请输入不少于6个字符', trigger: 'blur' },
                        ],
                        repeatPassword: [
                            {required: true, message: '请输入旧密码', trigger: 'blur'},
                            {validator: this.checkRepeatPassword, trigger: 'blur'}
                        ],
                    },
                }
            }
        },
        methods:{
            checkRepeatPassword(rule, value, callback) {
                if (value != this.formPassword.data.newPassword) {
                    callback(new Error('重新输入密码和新密码不一致'))
                } else {
                    callback()
                }
            },
            handlePassword(){
                if (this.$refs.formPassword) this.$refs.formPassword.resetFields()
                this.formPassword.visible = true
            },
            handlePasswordSubmit() {
                this.$refs.formPassword.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formPassword.loading = true
                            let para = Object.assign({}, this.formPassword.data)
                            para.checkPass1 = this.formPassword.data.newPassword
                            para.password = this.formPassword.data.oldPassword
                            this.$axios({
                                url: '/settings/password',
                                method: 'post',
                                data: para
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message.success("提交成功")
                                    this.formPassword.visible = false
                                }else {
                                    this.$message.error(res.msg)
                                }
                                this.formPassword.loading = false
                            })
                        })
                    }
                })
            },
        }
    }
</script>

<style scoped>

</style>
