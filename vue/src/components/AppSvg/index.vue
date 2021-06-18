<template>
    <svg :class="iconStyle" aria-hidden="true">
        <use :xlink:href="iconName"></use>
    </svg>
</template>

<script>
    export default {
        name: "AppSvg",
        props: {
            iconStyle: {
                type: String,
                default: 'icon'
            },
            iconClass: {
                type: String,
            },
            iconText:{
                type: String
            }
        },
        computed: {
            iconName() {
                if (!this.iconText){
                    return `#icon-${this.iconClass}`
                }
                let icon;
                switch (this.iconText.toLowerCase()) {
                    case 'pdf':
                    case 'xls':
                    case 'ppt':
                    case 'txt ':
                    case 'jpg':
                      icon = this.iconText.toLowerCase()
                      break;
                    case 'png':
                    case 'bmp':
                    case 'jpeg':
                    case 'gif':
                        icon = 'jpg'
                        break;
                    case 'xlsx':
                        icon = 'xls'
                        break;
                    case 'doc':
                    case 'docx':
                        icon = 'word'
                        break
                    case 'pptx':
                        icon = 'ppt'
                        break
                    case 'PDF':
                        icon = 'pdf'
                        break
                    case 'dwg':
                        icon = 'cad'
                        break
                    case 'zip':
                    case 'rar':
                    case '7z':
                        icon = 'zip'
                        break
                    default:
                        icon = 'document'
                }
                return `#icon-${icon}`
            }
        }
    }
</script>

<style scoped>

</style>
