<template>
    <el-dialog  title="文件夹排序" :visible.sync="formSort.visible"  :close-on-click-modal="true" width="500px" @close="formClose">
        <el-form :model="formSort.data" label-width="120px"  ref="formSort">
            <el-table ref="table" v-loading="folderTable.loading" height="300px" :data="folderTable.body"  highlight-current-row
                      :cell-style="{padding:'5px'}" class="custom-table-cell"
                      :header-cell-style="{padding: '2px'}" >
                <el-table-column prop="name" label="文件名">
                    <template slot-scope="scope">
                            <div v-if="scope.row.permissionMap.read">
                                <app-svg iconClass="wenjianjia3"></app-svg>
                                <a href=" javascript:void(0) " @click="handleClickFolder(scope.$index, scope.row)"
                                   style="margin-left:10px;text-decoration-line:none;"><b>{{scope.row.name}}</b></a>
                            </div>
                            <div v-else>
                                <app-svg iconClass="wenjianjia3"></app-svg>
                                <span style="margin-left:10px;">{{scope.row.name}}</span>
                            </div>
                    </template></el-table-column>

                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button class="" type="text" size="mini"
                                   icon="el-icon-caret-top"
                                   @click="handleUpMove(scope.$index, scope.row)"
                                   circle></el-button>
                        <el-button class="" type="text" size="mini"
                                   icon="el-icon-caret-bottom"
                                   @click="handleDownMove(scope.$index, scope.row)"
                                   circle></el-button>
                        <el-tooltip v-if="scope.row.disabled === false" class="item" effect="dark" content="隐藏文件夹" placement="bottom">
                            <el-button  type="text" size="mini"
                                       icon="el-icon-view"
                                       @click="handleFolderHide(scope.$index, scope.row)"
                                       circle></el-button>
                        </el-tooltip>
                        <el-tooltip v-if="scope.row.disabled === true" class="item" effect="dark" content="显示文件夹" placement="bottom">
                            <el-button  class="" type="text" size="mini"
                                        icon="el-icon-view" style="color:grey"
                                        @click="handleFolderHide(scope.$index, scope.row)"
                                        circle></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click.native="formSort.visible = false">关闭</el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "SortFolder",
        props: {
            sorted: {
                type: Function,
                default: null
            },
        },
        data() {
            return {
                formSort: {
                    data: {},
                    visible: false,
                    isSorted: false,
                },
                folderTable:{
                    loading: false,
                    body: [],
                    height: 0,
                    pageSize: 20,
                    total: 0,
                    curPage: 0,
                    multipleSelection: [],
                },
                selectFolder : ''
            }
        },
        methods :{
            handleSort(selectFolder) {
                this.selectFolder = selectFolder
                this.formSort.visible = true
                this.formSort.isSorted = false
                this.refresh()
            },
            tableHeight(){
                this.folderTable.height = this.$refs.formSort.height
            },
            // 获取table数据
            getData() {
                if (!this.selectFolder) return
                this.$axios({
                    url: `/user/folder/${this.selectFolder.id}/children`,
                    method: 'get'
                }).then(res => {
                    let permissionBody = [];
                    if (this.selectFolder.permissionMap && !this.selectFolder.permissionMap.visible) {
                        for( let i = 0; i < res.ctt.length; i++){
                            if (res.ctt[i].permissionMap.visible){
                                permissionBody.push(res.ctt[i])
                            }
                        }
                    }else {
                        permissionBody = res.ctt
                    }
                    permissionBody.forEach(c => {
                        if (c.disabled === null) c.disabled = false
                    })
                    this.folderTable.body = permissionBody
                    this.folderTable.total = res.ctt.total
                    this.folderTable.loading = false

                    this.$nextTick(() => {
                        this.tableHeight()
                    })
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            refresh(){
                this.folderTable.total = 0
                this.folderTable.body = []
                this.folderTable.curPage = 0
                this.getData()
            },
            handleUpMove(index, row) {
                if (index === 0) return
                let data = row
                let data2 = this.folderTable.body[index - 1]
                this.folderTable.body[index] = data2
                this.folderTable.body[index - 1] = data
                this.$refs.table.setCurrentRow(null)
                this.moveSubmit(row.id, '/user/folder/up')
            },
            handleDownMove(index, row) {
                if (index === this.folderTable.body.length - 1) return
                let data = row
                let data2 = this.folderTable.body[index + 1]
                this.folderTable.body[index] = data2
                this.folderTable.body[index + 1] = data
                this.$refs.table.setCurrentRow(null)
                this.moveSubmit(row.id, '/user/folder/down')
            },
            moveSubmit(id, url) {
                this.$axios({
                    url: url,
                    method: 'put',
                    data: {id: id}
                }).then((res) => {
                    this.formSort.isSorted = true
                    if(res.c === 0){
                    }else {
                        this.$message.error(res.msg)
                    }
                })
            },
            handleFolderHide(index, row){
                let url ;
                if (row.disabled === true) {
                    row.disabled = false
                    url = '/user/folder/display'
                } else {
                    row.disabled = true
                    url = '/user/folder/disabled'
                }
                this.moveSubmit(row.id, url)
            },
            formClose(){
                if (this.formSort.isSorted){
                    location.reload()
                }
            }
        }

    }
</script>

<style scoped>

</style>
