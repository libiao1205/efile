<template>
    <el-dialog title="安全提醒" :visible.sync="formPassword.visible" :close-on-click-modal="true" width="500px">
        <div>您的密码已超出安全期限，是否修改密码？</div>
        <div slot="footer" class="dialog-footer">
            <el-button type="danger" @click.native="closeRemind" :loading="formPassword.loading">不再提示</el-button>
            <el-button type="primary" @click.native="updatePassword" :loading="formPassword.loading">去修改</el-button>
            <el-button @click.native="formPassword.visible = false">取消</el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "PasswordExceed",
        data(){
            return{
                formPassword:{
                    visible: false,
                    loading: false,
                },
                docIndexView:null,
            }
        },
        methods:{
            updatePassword(){
                if (this.docIndexView){
                    this.docIndexView.updatePassword();
                }
                this.formPassword.visible = false;
            },
            showPasswordExceedMsg(view){
                this.docIndexView = view;
                this.formPassword.visible = true;
            },
            closeRemind(){
                this.formPassword.loading = true
                this.$axios({
                    url: '/settings/updatePasswordRemind',
                    method: 'post',
                }).then((res) => {
                    if(res.c === 0){
                        this.$message.success("设置成功")
                        this.formPassword.visible = false
                    }else {
                        this.$message.error(res.msg)
                    }
                    this.formPassword.loading = false
                })
            }
        }
    }
</script>

<style scoped>

</style>
