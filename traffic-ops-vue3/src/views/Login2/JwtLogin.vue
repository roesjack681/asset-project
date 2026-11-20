<template>
  <div
    style="position: relative"
    :class="prefixCls"
    class="relative h-[100%]"
  >
    <div class="app-loading">
      <div class="app-loading-wrap">
        <div class="app-loading-title">
          <img src="/logo.gif" class="app-loading-logo" alt="Logo" />
          <div class="app-loading-title">正在加载</div>
        </div>
        <div class="app-loading-item">
          <div class="app-loading-outter"></div>
          <div class="app-loading-inner"></div>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>

import { useDesign } from '@/hooks/web/useDesign'
import { useRouter } from 'vue-router';
const { currentRoute, push } = useRouter()
defineOptions({ name: 'Login' })
import * as LoginApi from '@/api/login'
import * as authUtil from "@/utils/auth";
const redirect = ref<string>('')
const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('login')
const handleLogin = async (params) => {
  try {
    const res = await LoginApi.socialLogin2(params)
    if (!res) {
      return
    }
    authUtil.setToken(res)
    if (!redirect.value) {
      redirect.value = '/'
    }
    push({ path: redirect.value || permissionStore.addRouters[0].path })
  } finally {
  }
}
onBeforeMount(() => {
  const router = useRouter();
  const currentRoute = router.currentRoute.value;
  if(currentRoute.query.token) {
    handleLogin(currentRoute.query.token)
  }
})
</script>

<style lang="scss" scoped>
$prefix-cls: #{$namespace}-login;

.#{$prefix-cls} {
  overflow: auto;

  &__left {
    &::before {
      position: absolute;
      top: 0;
      left: 0;
      z-index: -1;
      width: 100%;
      height: 100%;
      background-image: url('@/assets/svgs/login-bg.svg');
      background-position: center;
      background-repeat: no-repeat;
      content: '';
    }
  }
}
</style>

<style lang="scss">
.dark .login-form {
  .el-divider__text {
    background-color: var(--login-bg-color);
  }

  .el-card {
    background-color: var(--login-bg-color);
  }
}

.homePage {
}
.loginloading {
  position: absolute;
  z-index: 100001;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #f0f2f5;
}
.app-loading {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}
</style>
<style scoped>
.app-loading {
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  background: #f0f2f5;
}

.app-loading .app-loading-wrap {
  position: absolute;
  top: 50%;
  left: 50%;
  display: flex;
  -webkit-transform: translate3d(-50%, -50%, 0);
  transform: translate3d(-50%, -50%, 0);
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.app-loading .app-loading-title {
  margin-bottom: 30px;
  font-size: 20px;
  font-weight: bold;
  text-align: center;
}

.app-loading .app-loading-logo {
  width: 100px;
  margin: 0 auto 15px auto;
}

.app-loading .app-loading-item {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 60px;
  vertical-align: middle;
  border-radius: 50%;
}

.app-loading .app-loading-outter {
  position: absolute;
  width: 100%;
  height: 100%;
  border: 4px solid #2d8cf0;
  border-bottom: 0;
  border-left-color: transparent;
  border-radius: 50%;
  animation: loader-outter 1s cubic-bezier(0.42, 0.61, 0.58, 0.41) infinite;
}

.app-loading .app-loading-inner {
  position: absolute;
  top: calc(50% - 20px);
  left: calc(50% - 20px);
  width: 40px;
  height: 40px;
  border: 4px solid #87bdff;
  border-right: 0;
  border-top-color: transparent;
  border-radius: 50%;
  animation: loader-inner 1s cubic-bezier(0.42, 0.61, 0.58, 0.41) infinite;
}

@-webkit-keyframes loader-outter {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }

  100% {
    -webkit-transform: rotate(360deg);
    transform: rotate(360deg);
  }
}

@keyframes loader-outter {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }

  100% {
    -webkit-transform: rotate(360deg);
    transform: rotate(360deg);
  }
}

@-webkit-keyframes loader-inner {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }

  100% {
    -webkit-transform: rotate(-360deg);
    transform: rotate(-360deg);
  }
}

@keyframes loader-inner {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }

  100% {
    -webkit-transform: rotate(-360deg);
    transform: rotate(-360deg);
  }
}
</style>
