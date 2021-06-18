<template>
    <div class="sys-login">
        <div class="login-area">
            <div class="logo">
                <img src="~sysStatic/images/logo.png" alt="">
            </div>
            <div class="form-group">
                <el-form :model="formData" :rules="linkRules" ref="linkForm" label-position="left" label-width="0px">
                    <el-form-item prop="password">
                        <el-input v-model="formData.password" type="password" placeholder="请输入提取码"></el-input>
                    </el-form-item>
                    <a class="btn-login" type="primary" @click="submitForm()">提取文件</a>
                </el-form>
                <div v-if="sysMsg" class="err-msg">{{sysMsg}}</div>
            </div>
            <div class="tip">
            </div>
        </div>
    </div>
</template>

<script>
    import Cookie from 'js-cookie'
    export default {
        data() {
            return {
                formData: {
                    password: '',
                },
                linkRules: {
                    password :[
                        {required: true, message: '提取码必须输入', trigger: 'blur'}
                    ],
                },
                sysMsg: '',
                linkRandom:'',
            }
        },
        mounted() {
            this.linkRandom = this.$route.query.id
            if (!this.linkRandom) this.$router.push('home')
        },
        methods: {
            submitForm(){
                this.$refs.linkForm.validate((valid) => {
                    if (valid) {
                        this.$router.push({path: 'linkpreview', query:{shared: this.linkRandom}})
                        Cookie.set(this.linkRandom, this.formData.password, {
                            expires: 1
                        })
                    } else {
                        return false
                    }
                });
            },
            setErrMsg(){
                // this.loginRules.name[0].message = this.$t('global.errMsg.inputRequired', {cont: this.$t('global.username')})
                // this.loginRules.password[0].message = this.$t('global.errMsg.inputRequired', {cont: this.$t('global.password')})
                // this.loginRules.captcha[0].message = this.$t('global.errMsg.inputRequired', {cont: this.$t('global.captcha')})
            }
        }
    }
</script>
