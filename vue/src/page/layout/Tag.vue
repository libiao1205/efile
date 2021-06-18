<template>
    <el-dialog title="我的标签" @open="getTag" :visible.sync="formTag.visible" :close-on-click-modal="true" >
        <div class="fxr pd5" style="flex-wrap:wrap;">
            <el-tag v-for="(project,id) in tagBar.body" closable type="warning" :key="project.id" class="mt10"
                    :disable-transitions="false" @click.native="tagSearch(project.name)"  @close="handleClose(project)">
                <a href=" javascript:void(0)" style="margin-left:10px;text-decoration-line:none;">{{project.name}}</a>
            </el-tag>
            <el-input :span="1" class="input-new-tag mt10" v-if="tagBar.inputVisible" v-model="tagBar.inputValue"
                      ref="saveTagInput" size="small"
                      @keyup.enter.native="handleInputConfirm"
                      @blur="handleInputConfirm"></el-input>
            <el-button :span="3" v-else class="button-new-tag mt10" size="small" @click="showInput" >+ 点击添加新标签
            </el-button>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button @click.native="formTag.visible = false">关闭</el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "Tag",
        data() {
            return{
                formTag:{
                    data:{
                        id: 0 ,
                        name:'',
                        parentId:'',
                        parentName: '',
                    },
                    visible: false,
                },
                tagBar:{
                    loading: true,
                    body: [],
                    inputVisible: false,
                    inputValue: '',
                },
            }
        },
        methods:{
            handleTag(){
                this.formTag.visible = true
            },
            getTag() {
                this.tagBar.loading = true
                this.$axios({
                    url: `/user/tags`,
                    method: 'get'
                }).then(res => {
                    this.tagBar.body = res.ctt
                    this.tagBar.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //添加新标签
            showInput() {
                this.tagBar.inputVisible = true;
                this.$nextTick(_ => {
                    this.$refs["saveTagInput"].$refs.input.focus();
                });
            },
            //提交新标签
            handleInputConfirm() {
                let newValue = {
                    id: 0,
                    name: this.tagBar.inputValue,
                    type:2
                } ;
                if (newValue.name) {
                    this.tagBar.loading = true
                    this.$axios({
                        url: `/user/tag`,
                        method: 'post',
                        data: newValue
                    }).then(res => {
                        if(res.c === 0){
                            this.$message.success("已添加新标签")
                            newValue.id = res.ctt.id;
                            this.tagBar.body.push(newValue);
                            this.tagBar.loading = false
                        }else {
                            this.$message.error(res.msg)
                        }
                    }).catch(err => {

                    })
                }
                this.tagBar.inputVisible = false;
                this.tagBar.inputValue = '';
            },
            //删除标签
            handleClose(tag) {
                this.$confirm('删除这个标签?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.tagBar.loading = true;
                    let para = tag;
                    this.$axios({
                        url: `user/tag`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.tagBar.body.splice(this.tagBar.body.indexOf(tag), 1);
                        this.tagBar.loading = false;
                    })
                }).catch(() => {

                });
            },
            tagSearch(name){
                this.formTag.visible = false
                this.$router.push({path:'search', query:{ searchKey: name}})
            }
        }
    }
</script>

<style scoped>

</style>
