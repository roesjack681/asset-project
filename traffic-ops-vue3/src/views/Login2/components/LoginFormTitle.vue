<template>
  <h1 class="login-title" style="color: #204BE9;font-size: 16px;text-align: center">
    账号密码登录
  </h1>
</template>
<script lang="ts" setup>
import { LoginStateEnum, useLoginState } from './useLogin'

defineOptions({ name: 'LoginFormTitle' })

const { t } = useI18n()

const { getLoginState } = useLoginState()

const getFormTitle = computed(() => {
  const titleObj = {
    [LoginStateEnum.RESET_PASSWORD]: t('sys.login.forgetFormTitle'),
    [LoginStateEnum.LOGIN]: t('sys.login.signInFormTitle'),
    [LoginStateEnum.REGISTER]: t('sys.login.signUpFormTitle'),
    [LoginStateEnum.MOBILE]: t('sys.login.mobileSignInFormTitle'),
    [LoginStateEnum.QR_CODE]: t('sys.login.qrSignInFormTitle'),
    [LoginStateEnum.SSO]: t('sys.login.ssoFormTitle')
  }
  return titleObj[unref(getLoginState)]
})
</script>
<style scoped lang="scss">
.login-title {
  position: relative;
  &:after {
    content: '';
    position: absolute;
    width: 15px;
    height: 5px;
    background: #204BE9;
    bottom: 0;
    left: 50%;
    border-radius: 4px;
    transform: translate(-50%);
  }
}
</style>
