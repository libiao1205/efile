<template>
    <el-main ref="main" v-loading="loading" v-show="isShow" style="padding:0;overflow: hidden" :style="{ height}">
        <iframe v-show="pdfFrameVisible" ref="pdfFrame" style="padding-left: -30px; left: 0px; top: 0px; width: 100%; border: none;" :style="{height}"
                allowtransparency="true"
                allowfullscreen="true"
                webkitallowfullscreen="true"
                mozallowfullscreen="true">
        </iframe>
        <iframe v-show="officeFrameVisible" ref="officeFrame" style="padding-left: -30px; left: 0px; top: 0px; width: 100%; border: none;" :style="{height}"
                allowtransparency="true"
                allowfullscreen="true"
                webkitallowfullscreen="true"
                mozallowfullscreen="true">
        </iframe>
        <iframe v-show="imgFrameVisible" ref="imgFrame" style="padding-left: -30px; left: 0px; top: 0px; width: 100%; border: none;" :style="{height}"
                allowtransparency="true"
                allowfullscreen="true"
                webkitallowfullscreen="true"
                mozallowfullscreen="true">
        </iframe>
        <div v-show="suportVisible">
            <el-alert
                :closable="false"
                title="暂不支持预览"
                type="error">
            </el-alert>
        </div>
    </el-main>
</template>

<script>
    export default {
        name: "AppFrame",
        props: {
            height: {
                type: String,
                default: '100%',
            },
        },
        data() {
            return {
                frameUrl:'',
                loaded: false,
                loading: false,
                isShow: true,
                pdfFrameVisible: false,
                officeFrameVisible:false,
                suportVisible: false,
                imgFrameVisible:false,
            }
        },
        methods: {
            loadFile(suffix, fileUrl){
                this.suportVisible = false
                this.pdfFrameVisible = false
                this.officeFrameVisible = false
                this.imgFrameVisible = false
                if (this.loaded) this.isShow = false
                this.loaded = true
                this.loading = true
                switch (suffix.toLowerCase()) {
                    case 'txt':
                    case 'pdf':
                        this.pdfFrameVisible = true;
                        this.frameUrl = this.$pdf + fileUrl
                        this.$refs.pdfFrame.contentWindow.location.replace(this.frameUrl)
                        break
                    case 'pptx':
                    case 'ppt':
                    case 'docx':
                    case 'doc':
                    case 'xls':
                    case 'xlsx':
                        this.officeFrameVisible = true
                        this.frameUrl = this.$office + fileUrl
                        this.$refs.officeFrame.contentWindow.location.replace(this.frameUrl)
                        break
                    case 'jpg':
                    case 'jpeg':
                    case 'png':
                    case 'bmp':
                    case 'gif':
                        this.imgFrameVisible = true
                        this.frameUrl = fileUrl
                        this.$refs.imgFrame.contentWindow.location.replace(this.frameUrl)
                        break
                    default:
                        this.suportVisible = true
                        break
                }
                this.loading = false
                this.isShow = true
            }
        }
    }
</script>

<style scoped>

</style>
