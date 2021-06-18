<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="shezhi2"></app-svg>  系统设置</h1>
                </div>
            </div>
        </el-header>
        <el-main class="sys-content pd0">
                <div class="pl10">
                    <el-collapse accordion style="width: 1000px" @change="collapseChange">
                        <el-collapse-item name="1" >
                            <template slot="title">
                                <span class="spanClass"> <i class="iconfont icon-denglumima"></i> 密码配置</span>
                            </template>
                            <el-card shadow="always" class="mg20" >
                                <el-collapse v-model="activeName" @change="handleChange" accordion>
                                    <!--<el-collapse-item title="密码变更提醒" name="1">
                                        <el-form :model="sysPasswordChangeSetting" label-width="140px" :rules="sysPasswordChangeSetting.rules" ref="formAddUser">
                                            <el-form-item label="提醒周期" prop="deptName">
                                                <el-input class="inputClass" v-model="sysPasswordChangeSetting.period"></el-input>
                                            </el-form-item>
                                            <el-form-item label="是否提醒" prop="deptName">
                                                <el-checkbox :v-model="true"></el-checkbox>
                                            </el-form-item>
                                        </el-form>
                                        <el-button type="primary" plain>确定</el-button>
                                    </el-collapse-item>-->
                                    <el-collapse-item title="密码设置" name="1">
                                        <el-form v-loading="loading" :model="sysPasswordChangeSetting" label-width="140px" :rules="sysPasswordChangeSetting.rules" ref="formAddUser">
                                            <el-form-item label="密码最小长度" prop="deptName">
                                                <el-input class="inputClass" v-model="sysPasswordChangeSetting.limit"></el-input>
                                            </el-form-item>
                                            <el-form-item label="密码复杂度">
                                                <el-radio-group v-model="sysPasswordChangeSetting.model">
                                                    <el-radio class="radio" :label="0" border>任意组合</el-radio>
                                                    <el-radio class="radio" :label="1" border>字母大写、字母小写、数字组合</el-radio>
                                                    <el-radio class="radio" :label="2" border>字母、数字、特殊字符组合</el-radio>
                                                </el-radio-group>
                                            </el-form-item>
                                        </el-form>
                                        <el-button type="primary" @click="passwordComplexitySubmit" plain>确定</el-button>
                                    </el-collapse-item>
                                    <el-collapse-item title="密码修改提醒设置" name="2">
                                        <el-form v-loading="loading" :model="sysPasswordChangeSetting" label-width="140px" :rules="sysPasswordChangeSetting.rules" ref="formAddUser">
                                            <el-form-item label="强制密码修改天数" prop="deptName">
                                                <el-input class="inputClass" v-model="sysPasswordChangeSetting.changeDate"></el-input>
                                            </el-form-item>
                                            <el-form-item label="提醒提前天数" prop="deptName">
                                                <el-input class="inputClass" v-model="sysPasswordChangeSetting.warnDate"></el-input>
                                            </el-form-item>
                                            <el-form-item label="是否启用" prop="deptName">
                                                <el-checkbox @change="sysPasswordChangeSetting.state = !sysPasswordChangeSetting.state" :value="sysPasswordChangeSetting.state"></el-checkbox>
                                            </el-form-item>
                                        </el-form>
                                        <el-button type="primary" @click="passwordExceedSubmit" plain>确定</el-button>
                                    </el-collapse-item>
                                    <el-collapse-item title="密码锁定设置" name="3">
                                        <el-form v-loading="loading" :model="sysPasswordChangeSetting" label-width="140px" :rules="sysPasswordChangeSetting.rules" ref="formAddUser">
                                            <el-form-item label="启用密码锁定" prop="deptName">
                                                <el-checkbox @change="sysPasswordChangeSetting.state = !sysPasswordChangeSetting.state" :value="sysPasswordChangeSetting.state"></el-checkbox>
                                            </el-form-item>
                                            <el-form-item label="锁定密码错误次数" prop="deptName">
                                                <el-input class="inputClass" v-model="sysPasswordChangeSetting.lock"></el-input>
                                            </el-form-item>
                                        </el-form>
                                        <el-button type="primary" @click="passwordMistakeCountSubmit" plain>确定</el-button>
                                    </el-collapse-item>
                                </el-collapse>
                            </el-card>
                        </el-collapse-item>
                        <el-collapse-item name="2">
                            <template slot="title">
                                <span class="spanClass"> <i class="el-icon-time"></i> 定时任务配置</span>
                            </template>
                            <el-card shadow="always" class="mg20" >
                                <el-collapse v-model="activeName" @change="handleChangeTiming" accordion>
                                    <el-collapse-item title="备份删除设置" name="1">
                                        <el-form :model="sysPasswordChangeSetting" label-width="140px" :rules="sysTimingChangeSetting.rules" ref="dataFormPassword">
                                            <el-form-item label="保存月数" >
                                                <el-input class="inputClass" v-model="sysTimingChangeSetting.month" :required="true" ></el-input>
                                            </el-form-item>
                                            <el-form-item label="是否开启">
                                                <el-checkbox @change="sysTimingChangeSetting.state = !sysTimingChangeSetting.state" :value="sysTimingChangeSetting.state"></el-checkbox>
                                            </el-form-item>
                                        </el-form>
                                        <el-button type="primary" @click="TimingDeleteBackupSubmit" plain>确定</el-button>
                                    </el-collapse-item>
                                </el-collapse>
                            </el-card>
                        </el-collapse-item>
                        <el-collapse-item name="3">
                            <template slot="title">
                                <span class="spanClass"> <i class="el-icon-time"></i> logo与名称配置</span>
                            </template>
                            <el-card shadow="always" class="mg20" >
                                <el-collapse v-model="activeName" @change="handleChangeFolder" accordion>
                                    <el-collapse-item title="系统logo设置" name="1">
                                        <el-form v-loading="loading" :model="logoImgSetting" label-width="140px" :rules="logoImgSetting.rules">
                                            <el-form-item>
                                                <div class="fl">
                                                    <el-upload
                                                        ref="uploadImg"
                                                        list-type="picture-card"
                                                        :multiple="false"
                                                        :headers = "token"
                                                        :auto-upload="false"
                                                        :action="upload.url"
                                                        :limit="1"
                                                        :on-exceed="onUploadNum"
                                                        :before-upload="onUploadBefore"
                                                        :on-success="onUploadSuccess"
                                                        :on-error="onUploadError"
                                                    >
                                                        <i class="el-icon-plus"></i>
                                                    </el-upload>
                                                </div>
                                                <div class="fl ml10">
                                                    <img class="imgClass" :src="logoImgSetting.viewImageUrl"/>
                                                </div>
                                            </el-form-item>
                                        </el-form>
                                        <el-button type="primary" @click="handleAddFileSubmit" plain>确定</el-button>
                                    </el-collapse-item>
                                </el-collapse>
                                <el-collapse v-model="activeName" @change="handleChangeFolder" accordion>
                                    <el-collapse-item title="文档根节点设置" name="2">
                                        <el-form v-loading="loading" :model="folderRootSetting" label-width="140px" :rules="folderRootSetting.rules" ref="dataFormFolder">
                                            <el-form-item label="输入新的名称" prop="name">
                                                <el-input class="inputClass" v-model="folderRootSetting.name" :required="true" placeholder="请输入内容" auto-complete="off"></el-input>
                                            </el-form-item>
                                        </el-form>
                                        <el-button type="primary" @click="folderRootSubmit" plain>确定</el-button>
                                    </el-collapse-item>
                                </el-collapse>
                            </el-card>
                        </el-collapse-item>
<!--                        <el-collapse-item name="4">-->
<!--                            <template slot="title">-->
<!--                                <span class="spanClass"> <i class="el-icon-time"></i> 搜索引擎配置</span>-->
<!--                            </template>-->
<!--                            <el-card shadow="always" class="mg20" >-->
<!--                                &lt;!&ndash;<el-collapse v-model="activeName" @change="handleChangeSystemSwitch" accordion>&ndash;&gt;-->
<!--                                    &lt;!&ndash;<el-collapse-item title="是否支持结构化文档设置" name="1">&ndash;&gt;-->
<!--                                        &lt;!&ndash;<el-form v-loading="loading" :model="SystemSwitch" label-width="140px" :rules="SystemSwitch.rules">&ndash;&gt;-->
<!--                                            &lt;!&ndash;<el-form-item label="" prop="name">&ndash;&gt;-->
<!--                                                &lt;!&ndash;<el-switch&ndash;&gt;-->
<!--                                                    &lt;!&ndash;v-model="SystemSwitch.value"&ndash;&gt;-->
<!--                                                    &lt;!&ndash;active-text="支持"&ndash;&gt;-->
<!--                                                    &lt;!&ndash;inactive-text="不支持">&ndash;&gt;-->
<!--                                                &lt;!&ndash;</el-switch>&ndash;&gt;-->
<!--                                            &lt;!&ndash;</el-form-item>&ndash;&gt;-->
<!--                                        &lt;!&ndash;</el-form>&ndash;&gt;-->
<!--                                        &lt;!&ndash;<el-button type="primary" @click="constructionDataSubmit" plain>确定</el-button>&ndash;&gt;-->
<!--                                    &lt;!&ndash;</el-collapse-item>&ndash;&gt;-->
<!--                                &lt;!&ndash;</el-collapse>&ndash;&gt;-->
<!--                                <el-collapse v-model="activeName" @change="handleChangeSystemSwitch" accordion>-->
<!--                                    <el-collapse-item title="同步模式" name="2">-->
<!--                                        <el-form v-loading="loading" :model="SystemSwitch" label-width="140px">-->
<!--                                            <el-form-item label="" prop="name">-->
<!--                                                <el-radio-group v-model="SystemSwitch.value">-->
<!--                                                    <el-radio :label="1">即时同步</el-radio>-->
<!--                                                    <el-radio :label="2">每日晚间同步</el-radio>-->
<!--                                                </el-radio-group>-->
<!--                                            </el-form-item>-->
<!--                                        </el-form>-->
<!--                                        <el-button type="primary" @click="SolrSynchronizationSwitchSubmit" plain>确定</el-button>-->
<!--                                    </el-collapse-item>-->
<!--                                </el-collapse>-->
<!--                                <el-collapse v-model="activeName" @change="handleChangeSystemSwitch" accordion>-->
<!--                                    <el-collapse-item title="是否支持自定义搜索设置" name="3">-->
<!--                                        <el-form v-loading="loading" :model="SystemSwitch" label-width="140px">-->
<!--                                            <el-form-item label="" prop="name">-->
<!--                                                <el-input-->
<!--                                                        type="textarea"-->
<!--                                                        :rows="3"-->
<!--                                                        placeholder="请输入Solr查询语句"-->
<!--                                                        v-model="SystemSwitch.queryTemplate">-->
<!--                                                </el-input>-->
<!--                                            </el-form-item>-->
<!--                                        </el-form>-->
<!--                                        <el-button type="primary" @click="solrQueryTemplateSubmit" plain>确定</el-button>-->
<!--                                    </el-collapse-item>-->
<!--                                </el-collapse>-->
<!--                            </el-card>-->
<!--                        </el-collapse-item>-->
                    </el-collapse>
                </div>
        </el-main>
    </el-container>
</template>

<script>
    import Utils from '../../../util/utils'
    export default {
        name: "sys",
        computed: {
            token() {
                return {Authorization :'Bearer ' + this.$store.state.auth.token}
            },
        },
        data() {
            return {
                activeName: '1',
                sysPasswordChangeSetting:{
                    rules:{

                    },
                    period:0,
                    limit:6,
                    model:0,

                    changeDate:30,
                    warnDate:5,
                    state:false,

                    lock:0,

                },
                sysTimingChangeSetting:{
                    rules:{
                        saveMonth:[{required: true, message: '内容必填', trigger: 'blur'}]
                    },
                    month:2,
                    state:false,
                },
                loading: true,
                folderRootSetting:{
                    rules: {
                        name:[{required: true, message: '内容必填', trigger: 'blur'}]
                    },
                    id: 0,
                    name: '',
                },
                SystemSwitch:{
                    rules:{

                    },
                    value: 1,
                    queryTemplate:null
                },
                logoImgSetting: {
                    rules:{

                    },
                    viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
                },

                /*文件上传服务器路径 Start*/
                upload:{
                    url:`${this.$upload}/setting/logoImg`,
                    size: this.$maxImgSize
                },
                randNum: 0,
                /*文件上传服务器路径 End*/
            };
        },
        methods: {
            //开始文件上传
            handleAddFileSubmit(){
                this.$refs.uploadImg.submit();
            },
            onUploadNum(){
                this.$message({
                    message: '最多只能上传一张图片',
                    type: 'warning'
                });
            },
            onUploadBefore(file){
                const isPNG = file.type === 'image/png';
                const isLt2M = file.size / 1024 / 1024 < this.upload.size;

                if (!isPNG) {
                    this.$message.error('上传图片只能是 PNG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传图片大小不能超过 '+this.upload.size+'MB!');
                }
                return isPNG && isLt2M;
            },
            onUploadSuccess(){
                this.$message({
                    message: '图片上传成功',
                    type: 'success'
                });
                this.logoImgSetting.viewImageUrl = this.logoImgSetting.viewImageUrl+(new Date().getTime());
                Utils.$emit('refreshImg');
            },
            onUploadError(){
                this.$message.error('图片上传失败');
            },
            collapseChange(activeNames){
                if(activeNames === '1'){
                    this.getPasswordComplexity();
                }else if(activeNames === '2'){
                    this.getBackupPeriod();
                }else if(activeNames === '3'){
                    this.getFolderRoot();
                }else if(activeNames === '4'){
                    this.getConstructionData();
                }
            },
            handleChange(val) {
                if(val === '1'){
                    this.getPasswordComplexity();
                }else if(val === '2'){
                    this.getPasswordExceed();
                }else if(val === '3'){
                    this.getPasswordLock();
                }
            },
            handleChangeTiming(val){
                if(val === '1'){
                    this.getBackupPeriod();
                }
            },
            handleChangeFolder(val){
                if(val === '1'){

                }else if(val === '2'){
                    this.getFolderRoot();
                }
            },
            handleChangeSystemSwitch(val){
                if(val === '1'){
                    this.getConstructionData();
                }else if(val === '2'){
                    this.getSolrSynchronizationSwitch();
                }else if(val === '3'){
                    this.getSolrQueryTemplate();
                }
            },
            getPasswordComplexity(){
                this.loading = true;
               this.$axios({
                    url: '/setting/getPasswordComplexity',
                    method: 'get'
                }).then(res => {
                    let data = res.ctt;
                    this.sysPasswordChangeSetting.model = parseInt(data.psdCheck);
                    this.sysPasswordChangeSetting.limit = data.psdLength;
                    this.loading = false;
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            getPasswordExceed(){
                this.loading = true;
                this.$axios({
                    url: '/setting/getPasswordExceed',
                    method: 'get'
                }).then(res => {
                    let data = res.ctt;
                    this.sysPasswordChangeSetting.changeDate = data.usableDay;
                    this.sysPasswordChangeSetting.warnDate = data.aheadDay;
                    this.sysPasswordChangeSetting.state = data.state === '1'? true : false;
                    this.loading = false;
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            getPasswordLock(){
                this.loading = true;
                this.$axios({
                    url: '/setting/getPasswordLock',
                    method: 'get'
                }).then(res => {
                    let data = res.ctt;
                    if(data != null){
                        this.sysPasswordChangeSetting.lock = data.value;
                        this.sysPasswordChangeSetting.state = data.status === 1 ? true : false;
                    }else{
                        this.sysPasswordChangeSetting.lock = '';
                        this.sysPasswordChangeSetting.state = false;
                    }
                    this.loading = false;
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            passwordComplexitySubmit(){
                this.loading = true;
                let para = Object.assign({}, this.sysPasswordChangeSetting)
                para.psdCheck = this.sysPasswordChangeSetting.model;
                para.psdLength = this.sysPasswordChangeSetting.limit;
                this.$axios({
                    url: '/setting/passwordComplexity',
                    method: 'post',
                    data: para
                }).then((res) => {
                    if(res.c > 0){
                        this.$message.error(res.msg);
                    }else{
                        this.$message({
                            message: '设置成功',
                            type: 'success'
                        });
                    }
                    this.loading = false;
                })
            },
            passwordExceedSubmit(){
                this.loading = true;
                let para = Object.assign({}, this.sysPasswordChangeSetting)
                para.usableDay = this.sysPasswordChangeSetting.changeDate;
                para.aheadDay = this.sysPasswordChangeSetting.warnDate;
                para.state = this.sysPasswordChangeSetting.state ? 1 : 0;
                this.$axios({
                    url: '/setting/passwordUpdateRemind',
                    method: 'post',
                    data: para
                }).then((res) => {
                    if(res.c > 0){
                        this.$message.error(res.msg);
                    }else{
                        this.$message({
                            message: '设置成功',
                            type: 'success'
                        });
                    }
                    this.loading = false;
                })
            },
            passwordMistakeCountSubmit(){
                this.loading = true;
                let para = Object.assign({}, this.sysPasswordChangeSetting)
                para.psdMistakeCount = this.sysPasswordChangeSetting.lock;
                para.state = this.sysPasswordChangeSetting.state ? 1 : 0;
                this.$axios({
                    url: '/setting/passwordLock',
                    method: 'post',
                    data: para
                }).then((res) => {
                    if(res.c > 0){
                        this.$message.error(res.msg);
                    }else{
                        this.$message({
                            message: '设置成功',
                            type: 'success'
                        });
                    }
                    this.loading = false;
                })
            },
            //*****************定时配置**********************
            getBackupPeriod(){
                this.loading = true;
                this.$axios({
                    url: '/setting/getBackupPeriod',
                    method: 'get'
                }).then(res => {
                    let data = res.ctt;
                    if(data != null){
                        this.sysTimingChangeSetting.month = data.value;
                        this.sysTimingChangeSetting.state = data.status === 1 ? true : false;
                    }else{
                        this.sysTimingChangeSetting.month = '';
                        this.sysTimingChangeSetting.state = false;
                    }
                    this.loading = false;
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            TimingDeleteBackupSubmit(){
                this.$refs['dataFormPassword'].validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        let para = Object.assign({}, this.sysTimingChangeSetting)
                        para.month = this.sysTimingChangeSetting.month;
                        para.state = this.sysTimingChangeSetting.state ? 1 : 0;
                        this.$axios({
                            url: '/setting/deleteBackupPeriod',
                            method: 'post',
                            data: para
                        }).then((res) => {
                            if(res.c > 0){
                                this.$message.error(res.msg);
                            }else{
                                this.$message({
                                    message: '设置成功',
                                    type: 'success'
                                });
                            }
                            this.loading = false;
                        })
                    }
                });
            },
            //*****************文档根节点配置**********************
            getFolderRoot(){
                this.loading = true;
                this.$axios({
                    url: '/user/folder/0/children',
                    method: 'get'
                }).then(res => {
                    let data = res.ctt;
                    if(data.length > 0){
                        this.folderRootSetting.name = data[0].name;
                        this.folderRootSetting.id = data[0].id;
                    }else{
                        this.folderRootSetting.name = '';
                        this.folderRootSetting.id = 0;
                    }
                    this.loading = false;
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            folderRootSubmit(){
                this.$refs['dataFormFolder'].validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        let para = Object.assign({}, this.folderRootSetting)
                        this.$axios({
                            url: '/user/folder',
                            method: 'put',
                            data: para
                        }).then((res) => {
                            if(res.c > 0){
                                this.$message.error(res.msg);
                            }else{
                                this.$message({
                                    message: '设置成功',
                                    type: 'success'
                                });
                            }
                            this.loading = false;
                        })
                    }
                });
            },
            //**********************************************************系统开关**********************
            //*****************是否支持结构化数据**********************
            getConstructionData(){
                this.loading = true;
                this.$axios({
                    url: '/setting/getConstructionData',
                    method: 'get'
                }).then(res => {
                    let data = res.ctt;
                    if(data != null){
                        this.SystemSwitch.value = data.value === '1';
                    }else{
                        this.SystemSwitch.value = false;
                    }
                    this.loading = false;
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            constructionDataSubmit(){
                this.loading = true;
                let para = Object.assign({});
                para.value = this.SystemSwitch.value ? 1 : 0;
                this.$axios({
                    url: '/setting/constructionData',
                    method: 'post',
                    data: para
                }).then((res) => {
                    if(res.c > 0){
                        this.$message.error(res.msg);
                    }else{
                        this.$message({
                            message: '设置成功',
                            type: 'success'
                        });
                    }
                    this.loading = false;
                })
            },
            //*****************是否支持solr同步**********************
            getSolrSynchronizationSwitch(){
                this.loading = true;
                this.$axios({
                    url: '/setting/getSolrSynchronizationSwitch',
                    method: 'get'
                }).then(res => {
                    let data = res.ctt;
                    if(data != null){
                        this.SystemSwitch.value = parseInt(data.value);
                    }else{
                        this.SystemSwitch.value = 1;
                    }
                    this.loading = false;
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            SolrSynchronizationSwitchSubmit(){
                this.loading = true;
                let para = Object.assign({});
                para.value = this.SystemSwitch.value;
                this.$axios({
                    url: '/setting/solrSynchronizationSwitch',
                    method: 'post',
                    data: para
                }).then((res) => {
                    if(res.c > 0){
                        this.$message.error(res.msg);
                    }else{
                        this.$message({
                            message: '设置成功',
                            type: 'success'
                        });
                    }
                    this.loading = false;
                })
            },
            //*****************是否支持solr搜索**********************
          getSolrQueryTemplate(){
                this.loading = true;
                this.$axios({
                    url: '/setting/solrQueryTemplate',
                    method: 'get'
                }).then(res => {
                    let data = res.ctt;
                    if(data != null){
                        this.SystemSwitch.queryTemplate = data.value;
                    }else{
                        this.SystemSwitch.queryTemplate = "";
                    }
                    this.loading = false;
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
          solrQueryTemplateSubmit(){
                this.loading = true;
                let para = Object.assign({});
                para.queryTemplate = this.SystemSwitch.queryTemplate;
                this.$axios({
                    url: '/setting/solrQueryTemplate',
                    method: 'post',
                    data: para
                }).then((res) => {
                    if(res.c > 0){
                        this.$message.error(res.msg);
                    }else{
                        this.$message({
                            message: '设置成功',
                            type: 'success'
                        });
                    }
                    this.loading = false;
                })
            },
        }
    }
</script>

<style >
    .spanClass {
        font-weight:bold;
        font-size: large;
        color:#63acff ;
    }

    .inputClass {
        width: 180px;
    }

    .imgClass{
        width: 140px;
        height: 140px;
    }
</style>
