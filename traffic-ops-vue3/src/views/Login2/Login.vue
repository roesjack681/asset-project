<template>
  <div
    :class="prefixCls"
    class="logicontent relative h-[100%] lt-md:px-10px lt-sm:px-10px lt-xl:px-10px lt-xl:px-10px"
  >
    <div class="relative mx-auto h-full flex">
      <div
        style="background: transparent"
        :class="`${prefixCls}__left flex-1 relative p-30px lt-xl:hidden`"
      >
        <!-- 左边的背景图 + 欢迎语 -->
        <div class="h-[calc(100%-60px)] flex items-center justify-center">
          <TransitionGroup
            appear
            enter-active-class="animate__animated animate__bounceInLeft"
            tag="div"
          >
            <div style="text-align: left">
              <div style="color: #000000; font-size: 34px;font-weight: lighter">欢迎登录</div>
              <div style="color: #000000; font-size: 34px;font-weight: bold">资产运维管理平台</div>
            </div>
          </TransitionGroup>
        </div>
      </div>
      <div class="relative flex-1 p-30px lt-sm:p-10px" style="background: transparent">
        <!-- 右边的登录界面 -->
        <Transition appear enter-active-class="animate__animated animate__bounceInRight">
          <div
            class="m-auto h-full w-[100%] flex items-center at-2xl:max-w-360px at-lg:max-w-360px at-md:max-w-360px at-xl:max-w-360px"
          >
            <!-- 账号登录 -->
            <LoginForm class="m-auto h-auto p-20px lt-xl:(rounded-3xl light:bg-white)" />
          </div>
        </Transition>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { underlineToHump } from '@/utils'

import { useDesign } from '@/hooks/web/useDesign'
import { useAppStore } from '@/store/modules/app'
import { ThemeSwitch } from '@/layout/components/ThemeSwitch'
import { LocaleDropdown } from '@/layout/components/LocaleDropdown'

import { LoginForm, MobileForm, QrCodeForm, RegisterForm, SSOLoginVue } from './components'

defineOptions({ name: 'Login' })

const { t } = useI18n()
const appStore = useAppStore()
const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('login')
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
.login_left_tags {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
  span {
    color: #ffffff;
    padding: 6px 22px;
    border: 1px solid #ffffff;
    border-radius: 30px;
  }
}
.logicontent {
  background: url("./images/logobg.png");
  background-size: 100% 100%;
  height: 100%;
  overflow: hidden;
}
</style>
