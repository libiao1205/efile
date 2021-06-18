<template>
    <div>
        <!--新增界面-->
        <el-dialog title="通知设定" :visible.sync="noticeForm.visible" :close-on-click-modal="false" @open="getData" width="400px">
            <el-form :model="noticeForm.data" label-width="100px" ref="formNotice">
                <el-checkbox :indeterminate="noticeForm.isIndeterminate" v-model="noticeForm.all" @change="handleCheckAllChange">全部接受</el-checkbox>
                <div style="margin: 15px 0;"></div>
                <el-checkbox-group v-model="noticeForm.checked" @change="handleCheckedCitiesChange">
                    <el-form-item v-for="(option,index) in noticeForm.options" :key="index" class="mb10">
                        <el-checkbox :label="option" :key="option.key">
                            {{option.value}}
                        </el-checkbox>
                    </el-form-item>
                </el-checkbox-group>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-add-new" @click.native="editAndSave" :loading="noticeForm.loading">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="noticeForm.visible = false">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
        <!--新增界面-->

    </div>
</template>

<script>
    const ActionOptions = [
        {key:'updateNotice',value:'接受更新通知'},
        {key:'deleteNotice',value:'接受删除通知'},
        {key:'downLoadNotice',value:'接受下载通知'}
    ];
    export default {
        name: "noticeMessage",
        data() {
            return {
                noticeForm: {
                    all: false,
                    options: ActionOptions,
                    isIndeterminate: false,
                    checked: [],
                    visible: false,
                    loading: false,
                    id: 0
                }
            }
        },
        methods: {
            handleNotice(){
                this.noticeForm.visible = true
            },
            handleCheckAllChange(val) {
                this.noticeForm.checked = val ? ActionOptions : [];
                this.noticeForm.isIndeterminate = false;
            },
            handleCheckedCitiesChange(value) {
                let checkedCount = value.length;
                this.noticeForm.all = checkedCount === this.noticeForm.options.length;
                this.noticeForm.isIndeterminate = checkedCount > 0 && checkedCount < this.noticeForm.options.length;
            },
            getData(){
                this.$axios({
                    url: '/notice/user',
                    method: 'get'
                }).then(res => {
                    if(res.c === 0){
                        console.log(res)
                        this.noticeForm.id = res.ctt.id
                        for (let i = 0; i < ActionOptions.length; i++){
                            switch(ActionOptions[i].key){
                                case 'updateNotice':
                                    if (res.ctt.updatenotice)this.noticeForm.checked.push(ActionOptions[i])
                                    break;
                                case 'deleteNotice':
                                    if (res.ctt.deletenotice)this.noticeForm.checked.push(ActionOptions[i])
                                    break;
                                case 'downLoadNotice':
                                    if (res.ctt.downloadnotice)this.noticeForm.checked.push(ActionOptions[i])
                                    break;
                            }
                        }
                        this.handleCheckedCitiesChange(this.noticeForm.checked);
                    }else{
                        this.$message.error(res.msg);
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            editAndSave(){
                console.log(this.noticeForm.checked)
                let pram = {}
                pram.updatenotice = false;
                pram.deletenotice = false;
                pram.downloadnotice = false;
                for( let i = 0; i < this.noticeForm.checked.length; i++){
                    switch(this.noticeForm.checked[i].key){
                        case 'updateNotice':
                            pram.updatenotice = true;
                            break;
                        case 'deleteNotice':
                            pram.deletenotice = true;
                            break;
                        case 'downLoadNotice':
                            pram.downloadnotice = true;
                            break;
                    }
                }
                this.$axios({
                    url: '/notice/editUserSetting',
                    method: 'post',
                    data: {id: this.noticeForm.id,  updatenotice: pram.updatenotice , deletenotice: pram.deletenotice, downloadnotice: pram.downloadnotice}
                }).then(res => {
                    if(res.c === 0){
                        this.noticeForm.visible = false;
                    }else{
                        this.$message.error(res.msg);
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            }
        }
    }
</script>

<style scoped>

</style>
