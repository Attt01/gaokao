<template>
  <div class="app-wrapper" :class="classObj">
    <div class="main-container">
      <navbar></navbar>
      <app-main></app-main>
      <user-info-dialog></user-info-dialog>
      <footer></footer>
    </div>
  </div>
</template>

<script>
import { Navbar, AppMain, UserInfoDialog } from './components'
import ResizeMixin from './mixin/ResizeHandler'

export default {
  name: 'layout',
  components: {
    Navbar,
    AppMain,
    UserInfoDialog
  },
  mixins: [ResizeMixin],
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar
    },
    device() {
      return this.$store.state.app.device
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    }
  },
  methods: {
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/mixin.scss";
  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
    &.mobile.openSidebar{
      position: fixed;
      top: 0;
    }
  }
</style>
