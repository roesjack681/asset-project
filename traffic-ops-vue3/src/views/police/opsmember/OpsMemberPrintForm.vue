<template>
  <Dialog :title="dialogTitle" width="800" v-model="dialogVisible">
    <div id="printArea" class="content print-area">
      <div class="image-section">
        <img src="https://img0.baidu.com/it/u=582752653,3386538207&fm=253&fmt=auto&app=138&f=GIF?w=500&h=500" alt="qrcode" class="image" />
      </div>
      <div class="info-section">
        <p class="address">工业路</p>
        <p class="address">东向西</p>
        <p class="address">0001</p>
        <p class="phone-number">运维联系电话：13312341234</p>
      </div>
    </div>
    <template #footer>
      <el-button @click="printContent" type="primary" v-if="formType!='detail'" :disabled="formLoading">打 印</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { OpsMemberApi, OpsMemberVO } from '@/api/traffic/optmember'

/** 杆件 表单 */
defineOptions({ name: 'OpsMemberPrintForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  equName: undefined,
  equNumber: undefined,
  equType: undefined,
  jibLength: undefined,
  roadName: undefined,
  memberType: undefined,
  intersectionName: undefined,
  lng: undefined,
  lat: undefined,
  adress: undefined,
  installDate: undefined,
  manufacturer: undefined,
  deletedTime: undefined,
  unit: undefined,
  hight: undefined,
  barLength: undefined
})

const printContent = () => {
  window.print();
}

const formRules = reactive({
})

const options = ref([])
const loading = ref(false)

const formRef = ref() // 表单 Ref
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    equName: undefined,
    equNumber: undefined,
    equType: undefined,
    jibLength: undefined,
    roadName: undefined,
    memberType: undefined,
    intersectionName: undefined,
    lng: undefined,
    lat: undefined,
    adress: undefined,
    installDate: undefined,
    manufacturer: undefined,
    deletedTime: undefined,
    unit: undefined,
    hight: undefined,
    barLength: undefined
  }
  formRef.value?.resetFields()
}
</script>

<style scoped>

.content {
  display: flex;
  align-items: center;
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.image-section {
  margin-right: 20px;
}

.image {
  width: 300px; /* 图片宽度 */
  height: 300px; /* 图片高度自动 */
  border-radius: 8px; /* 圆角边框 */
}

.info-section {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.address {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.phone-number {
  font-size: 16px;
  color: #666;
}

/* 打印样式 */
@media print {
  @page {
    size: 90mm 60mm;
    margin: 0;
  }

  body * {
    visibility: hidden; /* 隐藏页面上的所有元素 */
  }

  .content, .content * {
    visibility: visible; /* 仅显示打印区域及其内容 */
  }

  .content {
    position: absolute;
    left: 0;
    top: 0;
    width: 90mm;
    height: 60mm;
  }

  .container {
    height: auto; /* 自动高度 */
  }

  .image {
    width: 35mm; /* 图片宽度 */
    height: 35mm; /* 图片高度自动 */
    border-radius: 4px; /* 圆角边框 */
  }
}

</style>