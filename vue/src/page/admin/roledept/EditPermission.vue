<template>
    <div>
        <!--编辑权限界面-->
        <el-dialog title="设定权限" :visible.sync="fromPermissionData.visible.modify" :close-on-click-modal="false" width="450px">
            <el-form :model="fromPermissionData.data" label-width="120px" :rules="fromPermissionData.rules" ref="fromPermissionData">
                <el-form-item label="编辑权限对象：" prop="name" style="font-style: oblique;font-weight:bold;">
                    <div v-if="fromPermissionData.data.type === 1">
                        <app-svg iconClass="drxx95"></app-svg>
                        <span style="font-style: normal;font-weight: normal;">{{this.fromPermissionData.data.name}}</span>
                    </div>
                    <div v-else>
                        <app-svg iconClass="drxx10"></app-svg>
                        <span style="font-style: normal;font-weight: normal;">{{this.fromPermissionData.data.name}}</span>
                    </div>
                </el-form-item>
                <el-form-item label="当前编辑资源：" prop="name" style="font-style: oblique;font-weight:bold;">
                    <div v-if="fromPermissionData.data.rolePermissions[0].code === 'folder'">
                        <app-svg iconClass="wenjianjia3"></app-svg>
                        <span style="font-style: normal;font-weight: normal;">{{this.fromPermissionData.data.rolePermissions[0].resourceName}}</span>
                    </div>
                    <div v-else>
                        <app-svg :iconText="fromPermissionData.data.rolePermissions[0].resourceSuffix"></app-svg>
                        <span style="font-style: normal;font-weight: normal;">{{this.fromPermissionData.data.rolePermissions[0].resourceName}}.{{this.fromPermissionData.data.rolePermissions[0].resourceSuffix}}</span>
                    </div>
                </el-form-item>
                <el-checkbox :indeterminate="fromPermissionData.permissionOptions.isIndeterminate" v-model="fromPermissionData.permissionOptions.all" @change="handleCheckAllChange">全选</el-checkbox>
                <div style="margin: 15px 0;"></div>
                <el-checkbox-group v-model="fromPermissionData.permissionOptions.checked" @change="handleCheckedCitiesChange">
                    <el-row>
                        <el-col :span="6" :offset="2" v-for="(option,index) in fromPermissionData.permissionOptions.options" :key="index"  style="margin-bottom:20px">
                            <el-checkbox :label="option" :key="option.key">
                                <!--<app-svg iconClass="drxx95"></app-svg>-->
                                {{option.value}}
                            </el-checkbox>
                        </el-col>
                    </el-row>
                </el-checkbox-group>
                <div style="margin: 15px 0 0 15px; padding: 5px; display: flex; height: 100px;border-radius:5px;border: 1px solid #dcdfe6;">
                    <span style="color: #606266;font-weight:bold;">说明：<br/>
                        1 、为文件夹设置权限后，它的子文件/子文件夹都将继承这个权限，除非其有自己的权限设置。<br/>
                        2、对部门设置了某个文件/文件夹的权限，则它的子部门及用户都将继承这个权限，除非其有自己的权限设置。<br/>
                    </span>
                </div>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-add-new" @click.native="handlePermissionAddSubmit" :loading="fromPermissionData.loading">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="handlePermissionCancel">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
        <!--编辑文件夹界面-->
    </div>
</template>

<script>
    const ActionOptions = [
        {key:'visible',value:'可见'},
        {key:'new',value:'上传新文件'},
        {key:'modify',value:'重命名'},
        {key:'read',value:'预览'},
        {key:'update',value:'文件更新'},
        {key:'link',value:'文件外链'},
        {key:'download',value:'文件下载'},
        {key:'delete',value:'删除'},
    ];
    export default {
        name: "EditPermission",
        props: {
            editPermission: {
                type: Function,
                default: null
            },
        },
        data() {
            return{
                fromPermissionData:{
                    data:{
                        id:0,
                        userId:0,
                        deptId:0,
                        type:0,
                        name:'',
                        rolePermissions:[{
                            id:0,
                            roleId:0,
                            code:'',
                            resourceId:0,
                            resourceName:'',
                            resourceSuffix:'',
                            fullPath:'',
                            fullName:'',
                            explication: {
                                delete: false,
                                download: false,
                                link: false,
                                modify: false,
                                new: false,
                                read: false,
                                update: false,
                                visible: false,
                            }
                        }]
                    },
                    permissionOptions:{
                        all: false,
                        checked: [],
                        options: ActionOptions,
                        isIndeterminate: true,
                    },
                    rules:{},
                    visible:{
                        new:false,
                        modify:false,
                    },
                    loading: false,
                },
            }
        },
        methods :{
            handlePermissionEdit: function (document, member, roles) {
                this.fromPermissionData.visible.modify = true;
                this.fromPermissionData.data = {
                    id:0,
                    userId: member.kind === 'user'? member.id.id:0,
                    deptId:member.kind === 'dept'? member.id:0,
                    type:member.kind === 'dept'? 1:2,
                    name:member.name,
                    rolePermissions:[{
                        id:member.id,
                        roleId:0,
                        code:document.kind,
                        resourceId:document.id,
                        resourceName:document.name,
                        resourceSuffix:document.suffix,
                        fullPath:document.fullPath,
                        fullName:document.fullName,
                        explication: roles
                    }]
                }
                this.fromPermissionData.data.userId = member.kind === 'user'? member.id:0,
                    this.fromPermissionData.data.deptId = member.kind === 'dept'? member.id:0,
                    this.fromPermissionData.data.type = member.kind === 'dept'? 1:2,
                    this.fromPermissionData.data.name = member.name

                let checked = [];
                for( let i = 0; i < ActionOptions.length; i++){
                    switch(ActionOptions[i].key){
                        case 'delete':
                            if (this.fromPermissionData.data.rolePermissions[0].explication.delete)checked.push(ActionOptions[i])
                            break;
                        case 'download':
                            if (this.fromPermissionData.data.rolePermissions[0].explication.download)checked.push(ActionOptions[i])
                            break;
                        case 'link':
                            if (this.fromPermissionData.data.rolePermissions[0].explication.link)checked.push(ActionOptions[i])
                            break;
                        case 'modify':
                            if (this.fromPermissionData.data.rolePermissions[0].explication.modify)checked.push(ActionOptions[i])
                            break;
                        case 'new':
                            if (this.fromPermissionData.data.rolePermissions[0].explication.new)checked.push(ActionOptions[i])
                            break;
                        case 'read':
                            if (this.fromPermissionData.data.rolePermissions[0].explication.read)checked.push(ActionOptions[i])
                            break;
                        case 'update':
                            if (this.fromPermissionData.data.rolePermissions[0].explication.update)checked.push(ActionOptions[i])
                            break;
                        case 'visible':
                            if (this.fromPermissionData.data.rolePermissions[0].explication.visible)checked.push(ActionOptions[i])
                            break;
                    }
                }
                this.fromPermissionData.permissionOptions.checked = checked;
                this.handleCheckedCitiesChange(checked);
                if (this.$refs.fromPermissionData) this.$refs.fromPermissionData.resetFields()
            },
            handlePermissionAddSubmit:function(){
                this.$refs.fromPermissionData.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认新增权限吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.fromPermissionData.loading = true
                            let pram = Object.assign({}, this.fromPermissionData.data)
                            pram.rolePermissions[0].explication.delete = false;
                            pram.rolePermissions[0].explication.download = false;
                            pram.rolePermissions[0].explication.link = false;
                            pram.rolePermissions[0].explication.modify = false;
                            pram.rolePermissions[0].explication.new = false;
                            pram.rolePermissions[0].explication.read = false;
                            pram.rolePermissions[0].explication.update = false;
                            pram.rolePermissions[0].explication.visible = false;

                            for( let i = 0; i < this.fromPermissionData.permissionOptions.checked.length; i++){
                                switch(this.fromPermissionData.permissionOptions.checked[i].key){
                                    case 'delete':
                                        pram.rolePermissions[0].explication.delete = true;
                                        break;
                                    case 'download':
                                        pram.rolePermissions[0].explication.download = true;
                                        break;
                                    case 'link':
                                        pram.rolePermissions[0].explication.link = true;
                                        break;
                                    case 'modify':
                                        pram.rolePermissions[0].explication.modify = true;
                                        break;
                                    case 'new':
                                        pram.rolePermissions[0].explication.new = true;
                                        break;
                                    case 'read':
                                        pram.rolePermissions[0].explication.read = true;
                                        break;
                                    case 'update':
                                        pram.rolePermissions[0].explication.update = true;
                                        break;
                                    case 'visible':
                                        pram.rolePermissions[0].explication.visible = true;
                                        break;
                                }
                            }
                            this.$axios({
                                url: '/admin/role',
                                method: 'post',
                                data: pram
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message.success("新增权限成功")
                                    this.fromPermissionData.visible.modify = false
                                    this.handleResetPermission()
                                    if (this.editPermission) this.editPermission()
                                }else {
                                    this.$message.error(res.msg)
                                }
                                this.fromPermissionData.loading = false
                            })
                        })
                    }
                })
            },
            handlePermissionCancel:function () {
                this.handleResetPermission()
                this.fromPermissionData.visible.new = false
                this.fromPermissionData.visible.modify = false
            },
            handleResetPermission:function(){
                this.fromPermissionData.data = {
                    id:0,
                    userId: 0,
                    deptId:0,
                    type:0,
                    name:'',
                    rolePermissions:[{
                        id:0,
                        roleId:0,
                        code:'',
                        resourceId:0,
                        resourceName:'',
                        resourceSuffix:'',
                        fullPath:'',
                        fullName:'',
                        explication: {
                            delete: false,
                            download: false,
                            link: false,
                            modify: false,
                            new: false,
                            read: false,
                            update: false,
                            visible: false,
                        }
                    }]
                }
                this.fromPermissionData.permissionOptions.all = false;
                this.fromPermissionData.permissionOptions.checked = [];
                this.fromPermissionData.permissionOptions.isIndeterminate = false;
                if (this.$refs.fromPermissionData) this.$refs.fromPermissionData.resetFields();
            },
            handleCheckAllChange(val) {
                this.fromPermissionData.permissionOptions.checked = val ? ActionOptions : [];
                this.fromPermissionData.permissionOptions.isIndeterminate = false;
            },
            handleCheckedCitiesChange(value) {
                let checkedCount = value.length;
                this.fromPermissionData.permissionOptions.all = checkedCount === this.fromPermissionData.permissionOptions.options.length;
                this.fromPermissionData.permissionOptions.isIndeterminate = checkedCount > 0 && checkedCount < this.fromPermissionData.permissionOptions.options.length;
            },
        }
    }
</script>

<style scoped>

</style>
