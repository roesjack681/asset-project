<template>
  <Dialog :title="dialogTitle" width="800" v-model="dialogVisible">
    <div id="printArea" class="label-container">
      <div 
        class="label-item" 
        v-for="(item, index) in formData" 
        :key="index" 
        :id="'printArea' + index"
      > 
        <!-- 中部内容区 -->
        <div class="content-area">
          <!-- 左侧二维码 -->
          <div class="qr-code-container">
            <img :src="item.qrcodeFile" alt="二维码" class="qr-code" />
          </div>
          
          <!-- 右侧信息 -->
          <div class="info-container">
            <div class="serial-number">{{item.serialPrefix}}{{ padLeftZero(item.serialNum, 5) }}</div>
            <div class="orgName">包头公安</div>
          </div>
        </div>
      </div>
      <div class="page-break"></div>
    </div>
    
    <template #footer>
      <el-button type="primary" @click="handlePrint">打 印</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
    
  </Dialog>
</template>
<script setup lang="ts">
import printJS from 'print-js'

import { OpsQrcodeApi, OpsQrcodePrintVO } from '@/api/traffic/opsqrcode'
/** 杆件 表单 */
defineOptions({ name: 'OpsQrCodePrint' })


const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const printType = ref('')
const formData = ref([{
  raodName: '',
  direction: '',
  serialNum: '',
  qrcodeFile: ''
}])

const padLeftZero = (serialNum, length) => {
  return String(serialNum).padStart(length, '0');
}

const handlePrint = () => {
  const length = formData.value.length
  const pageWidth = 70 * length;
  const css = `@media print { @page { size: ` + pageWidth + `mm 50mm; margin: 0; } .label-container { display: flex; flex-wrap: wrap; gap: 20px; } .label-item { width: 100%; height: 100%; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); } .road-name { padding: 5px; font-size: 32px; font-weight: bold; text-align: center; background-color: #f5f5f5; border-bottom: 1px solid #e0e0e0; } .content-area { display: flex; height: calc(100% - 45px); } .qr-code-container { flex: 1; display: flex; align-items: left; justify-content: center; padding: 5px; } .qr-code { width: 35mm; height: 35mm; object-fit: contain; display: flex; align-items: left; } .info-container { flex: 1; display: flex; flex-direction: column; align-items: left; padding: 2px; } .serial-number { font-size: 18px; font-weight: bold; margin-top: 46px;} .orgName { font-size: 18px; font-weight: bold; margin-top: 20px; }`;

  printJS({
    printable: 'printArea',
    type: 'html',
    maxWidth: '96%', 
    targetStyle: ['*'],
    style: css,
    copywin: true,
    width: pageWidth,
    height: '70mm',
    font_size: 'llllll' // 解决printJS无法设置字体大小
  })
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = '打印预览'
  printType.value = type
  resetForm()

  formLoading.value = true
  try {
    formData.value = await OpsQrcodeApi.queryPrintData({
      "printType" : type,
      "keyword" : id
    })
  } finally {
    formLoading.value = false
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    raodName: '',
    direction: '',
    serialNum: '',
    qrcodeFile: ''
  }
}
</script>

<style scoped>
.label-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.label-item {
  width: 100%;
  height: 100%;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.road-name {
  padding: 12px;
  font-size: 64px;
  font-weight: bold;
  text-align: center;
  background-color: #f5f5f5;
  border-bottom: 1px solid #e0e0e0;
}

.content-area {
  display: flex;
  height: calc(100% - 45px);
}

.qr-code-container {
  flex: 1;
  display: flex;
  align-items: left;
  justify-content: center;
  padding: 10px;
}

.qr-code {
  width: 300px;
  height: 300px;
  object-fit: contain;
  display: flex;
  align-items: left;
}

.info-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: left;
  padding: 5px;
}

.serial-number {
  font-size: 42px;
  font-weight: bold;
  margin-bottom: 20px;
  margin-top: 110px;
}

.orgName {
  font-size: 42px;
  color: #666;
  margin-top: 34px;
}

@media print{
  @page {
      size: 70mm 50mm;
      margin: 0;
  }

  .page_break {
    page-break-before: always;
    page-break-after: always;
  }

  .label-container {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;
  }

  .label-item {
      width: 100%;
      height: 100%;
      border: 1px solid #e0e0e0;
      border-radius: 8px;
      overflow: hidden;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  .road-name {
      padding: 5px;
      font-size: 32px;
      font-weight: bold;
      text-align: center;
      background-color: #f5f5f5;
      border-bottom: 1px solid #e0e0e0;
  }

  .content-area {
      display: flex;
      height: calc(100% - 45px);
  }

  .qr-code-container {
      flex: 1;
      display: flex;
      align-items: left;
      justify-content: center;
      padding: 5px;
  }

  .qr-code {
      width: 35mm;
      height: 35mm;
      object-fit: contain;
      display: flex;
      align-items: left;
  }

  .info-container {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: left;
    padding: 5px;
  }

  .serial-number {
    font-size: 42px;
    font-weight: bold;
    margin-bottom: 20px;
    margin-top: 110px;
  }

  .orgName {
    font-size: 42px;
    color: #666;
    margin-top: 34px;
  }

  .page-break {
    page-break-before: always;
  }
}
</style>