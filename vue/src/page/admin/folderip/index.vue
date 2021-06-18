<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="jueceliubaimingdan"></app-svg>  文件夹访问IP白名单</h1>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button type="primary" size="mini" icon="iconfont icon-add" plain  @click.native="$refs.add.handleAddWhiteList">  新增配置</el-button>
                </div>
            </div>
        </el-header>

        <el-main class="sys-content pd0">
            <el-scrollbar >
                <el-table ref="table" v-loading="folderIpData.loading" :data="folderIpData.body" highlight-current-row
                          :cell-style="{padding:'5px'}" class="custom-table-cell"
                          :header-cell-style="{padding: '2px'}" :height="folderIpData.height">
                    <el-table-column type="index" label="序号" width="64px" align="center" ></el-table-column>
                    <el-table-column label="文件夹名称" prop="name" width="350px" ></el-table-column>
                    <el-table-column label="文件夹路径" prop="parentPathName" ></el-table-column>
                    <el-table-column label="操作" align="center" width="150px">
                        <template slot-scope="scope">
                            <div>
                                <el-tooltip class="item" effect="dark" content="编辑白名单" placement="bottom">
                                    <el-button class="btnEdit" type="text" size="mini"
                                               icon="iconfont icon-edit-file"
                                               @click.native="$refs.edit.handleEditWhiteList(scope.row)"
                                               circle></el-button>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="删除白名单配置" placement="bottom">
                                    <el-button class="btnDel" type="text" size="mini"
                                               icon="iconfont icon-trash"
                                               @click.native="deleteFolderIpData(scope.row)"
                                               circle></el-button>
                                </el-tooltip>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </el-scrollbar>
        </el-main>
        <add-folder-white ref="add" :add-job="getData"></add-folder-white>
        <edit-folder-white ref="edit" :edit-job="getData"></edit-folder-white>
    </el-container>
</template>

<script>
    import AddFolderWhite from "./AddFolderWhite";
    import EditFolderWhite from "./EditFolderWhite";
    export default {
        name: 'log',
        components: {AddFolderWhite,EditFolderWhite},
        data() {
            return {
                /*用户组表格数据 Start*/
                folderIpData: {
                    height: 0,
                    loading: true,
                    body: [],
                    pageSize: 1000,
                    total: 0,
                    curPage:0
                },
                /*人员表格数据 End*/

            }
        },
        mounted() {
            this.getData()
            this.tableHeight()
        },
        methods: {
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    this.folderIpData.height = window.innerHeight - this.$refs.table.$el.offsetTop - 200;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.folderIpData.height = window.innerHeight - self.$refs.table.$el.offsetTop - 200
                    }
                })
            },
            //画面刷新
            refresh(){
                this.getData()
            },
            // 获取table数据
            getData() {
                this.folderIpData.loading = true
                this.$axios({
                    url: `admin/folder/ips`,
                    method: 'get'
                }).then(res => {
                    this.folderIpData.body = res.ctt
                    this.folderIpData.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            // 获取table数据
            deleteFolderIpData(row) {
                this.$confirm('确认删除该白名单信息吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `/admin/folder/${para.id}/ip`,
                        method: 'delete',
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message({
                                message: '白名单信息已删除',
                                type: 'success'
                            });
                        }else{
                            this.$message.error(res.msg)
                        }
                        this.refresh()
                    })
                }).catch(() => {

                });
            },
        }
    }
</script>

<style scoped>

</style>
