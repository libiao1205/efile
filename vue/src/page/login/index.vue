<template>
    <div class="sys-login">
        <div class="login-area">
            <el-card shadow="hover">
                <div class="logo">
                    <img :src="viewImageUrl" alt=""/>
                </div>
                <div class="form-group">
                    <el-form :model="loginForm" :rules="loginRules" ref="loginForm" label-position="left" label-width="0px">
                        <el-form-item prop="name">
                            <el-input ref="nameInput" v-model="loginForm.name" type="text" :placeholder="$t('global.username')" @keyup.enter.native = "passwordInputFocus"></el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input ref="passwordInput" v-model="loginForm.password" type="password" :placeholder="$t('global.password')" @keyup.enter.native = "submitForm"></el-input>
                        </el-form-item>
                        <el-form-item prop="captcha" v-if="captcha.show" class="captcha">
                            <img :src="captcha.src" alt="">
                            <el-input v-model="loginForm.captcha" type="text" :placeholder="$t('global.captcha')"></el-input>
                        </el-form-item>
                        <a class="btn-login" type="primary" @click="submitForm()">{{$t('global.login')}}</a>
                    </el-form>
                    <div v-if="sysMsg" class="err-msg">{{sysMsg}}</div>
                </div>
                <div class="tip"></div>
            </el-card>
        </div>
    </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
    data() {
        return {
            loginForm: {
                name: '',
                password: '',
                captcha: ''
            },
            loginRules: {
                name: [
                    {required: true, message: '', trigger: 'blur'}
                ],
                password :[
                    {required: true, message: '', trigger: 'blur'}
                ],
                captcha: [
                    {required: false, message: '', trigger: 'blur'}
                ]
            },
            captcha: {
                show: false,
                src: ''
            },
            sysMsg: '',
            rep:'',
            viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
        }
    },
    computed: {
        ...mapState({
            lang: state => state.lang,
            theme: state => state.theme
        })
    },
    watch: {
        "captcha.show"(val){
            this.loginRules.captcha[0].required = val
        }
    },
    beforeMount(){
        // 初始化错误信息。保证单独点击input时可以弹出正确的错误提示
        this.setErrMsg()
    },
    mounted() {
        this.rep = this.$route.query.rep
        this.$refs.nameInput.focus()
    },
    methods: {
        ...mapActions({
            login: 'auth/loginByEmail',
            loadLang: 'loadLang'
        }),
        passwordInputFocus:function(){
            this.$refs.passwordInput.focus()
        },
        submitForm(){
            let that = this
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    this.login({
                        name: this.loginForm.name,
                        password: this.loginForm.password
                    }).then(res => {
                        if(res.login){
                            if (this.rep){
                                this.$router.push({path:'share', query:{id: this.rep}})
                            } else {
                                this.$router.push('home')
                            }
                        } else {
                            this.sysMsg = res.message
                            // this.captcha.show = true
                            // this.captcha.src = res.captcha
                        }
                    })
                } else {
                    return false
                }
            });
        },
        setErrMsg(){
            this.loginRules.name[0].message = this.$t('global.errMsg.inputRequired', {cont: this.$t('global.username')})
            this.loginRules.password[0].message = this.$t('global.errMsg.inputRequired', {cont: this.$t('global.password')})
            this.loginRules.captcha[0].message = this.$t('global.errMsg.inputRequired', {cont: this.$t('global.captcha')})
        }
    }
}
</script>


<style lang="scss">
    .sys-login {
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
        background: #162c49;  // #092756
        background: -moz-radial-gradient(0% 100%, ellipse cover, rgba(104,128,138,.4) 10%,rgba(138,114,76,0) 40%),-moz-linear-gradient(top,  rgba(57,173,219,.25) 0%, rgba(42,60,87,.4) 100%), -moz-linear-gradient(-45deg,  #670d10 0%, #162c49 100%);
        background: -webkit-radial-gradient(0% 100%, ellipse cover, rgba(104,128,138,.4) 10%,rgba(138,114,76,0) 40%), -webkit-linear-gradient(top,  rgba(57,173,219,.25) 0%,rgba(42,60,87,.4) 100%), -webkit-linear-gradient(-45deg,  #670d10 0%,#162c49 100%);
        background: -o-radial-gradient(0% 100%, ellipse cover, rgba(104,128,138,.4) 10%,rgba(138,114,76,0) 40%), -o-linear-gradient(top,  rgba(57,173,219,.25) 0%,rgba(42,60,87,.4) 100%), -o-linear-gradient(-45deg,  #670d10 0%,#162c49 100%);
        background: -ms-radial-gradient(0% 100%, ellipse cover, rgba(104,128,138,.4) 10%,rgba(138,114,76,0) 40%), -ms-linear-gradient(top,  rgba(57,173,219,.25) 0%,rgba(42,60,87,.4) 100%), -ms-linear-gradient(-45deg,  #670d10 0%,#162c49 100%);
        background: -webkit-radial-gradient(0% 100%, ellipse cover, rgba(104,128,138,.4) 10%,rgba(138,114,76,0) 40%), linear-gradient(to bottom,  rgba(57,173,219,.25) 0%,rgba(42,60,87,.4) 100%), linear-gradient(135deg,  #670d10 0%,#162c49 100%);
        filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#3E1D6D', endColorstr='#162c49',GradientType=1 );

        .login-area {
            padding: 0;
            //margin-bottom: 200px;
        }
    }
</style>
