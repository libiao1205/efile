<template>
    <div class="sys-section">
        <div class="title">
            <strong>{{title}}</strong>
            <slot></slot>
        </div>
    </div>
</template>

<script>
export default {
    name: 'AppSection',
    props: ['title']
}
</script>
