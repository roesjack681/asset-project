import html2canvas from "html2canvas";

import jsPDF from "jspdf";



export const downloadPDF = page => {

  html2canvas(page).then(function (canvas) {

    canvas2PDF(canvas);

  });

};

const getPdf = (id, title) => {
  html2Canvas(document.querySelector(`#${id}`), {
    useCORS: true //看情况选用上面还是下面的，
  }).then(function(canvas) {
    let contentWidth = canvas.width
    let contentHeight = canvas.height
    let pageHeight = contentWidth / 592.28 * 841.89
    let leftHeight = contentHeight
    let position = 0
    let imgWidth = 595.28
    let imgHeight = 592.28 / contentWidth * contentHeight
    let pageData = canvas.toDataURL('image/jpeg', 1.0)
    let PDF = new JsPDF('', 'pt', 'a4')
    if (leftHeight < pageHeight) {
      PDF.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight)
    } else {
      while (leftHeight > 0) {
        PDF.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
        leftHeight -= pageHeight
        position -= 841.89
        if (leftHeight > 0) {
          PDF.addPage()
        }
      }
    }
    PDF.save(title + '.pdf')
  })
}


const canvas2PDF = canvas => {

  let contentWidth = canvas.width;

  let contentHeight = canvas.height;



  //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高

  let imgWidth = 595.28;

  let imgHeight = 592.28 / contentWidth * contentHeight;

  //let imgHeight = 700/contentWidth * contentHeight;

  //一页pdf显示html页面生成的canvas高度;

  var pageHeight = contentWidth / 592.28 * 841.89;



  let totalHeight = contentHeight;

  // 第一个参数： l：横向  p：纵向

  // 第二个参数：测量单位（"pt"，"mm", "cm", "m", "in" or "px"）

  let pdf = new jsPDF("p", "pt");

  let position = 0;

  //   pdf.addImage(

  //     canvas.toDataURL("image/jpeg", 1.0),

  //     "JPEG",

  //     0,

  //     0,

  //     imgWidth,

  //     imgHeight

  //   );



  if (totalHeight < pageHeight) {

    pdf.addImage( canvas.toDataURL("image/jpeg", 1.0), 'JPEG', 0, 0, imgWidth, imgHeight);

  } else {

    while (totalHeight > 0) {

      pdf.addImage( canvas.toDataURL("image/jpeg", 1.0), 'JPEG', 0, position, imgWidth, imgHeight)

      totalHeight -= pageHeight;

      position -= 841.89;

      //避免添加空白页

      if (totalHeight > 0) {

        pdf.addPage();

      }

    }

  }

  // pdf.addImage(

  //     canvas.toDataURL("image/jpeg", 1.0),

  //     "JPEG",

  //     0,

  //     position,

  //     imgWidth,

  //     imgHeight

  // );




  pdf.save("导出.pdf");

};


export const exportPDF = (id) => {
  let dom = document.getElementById(id);
  // 获取所有子节点
  let children = dom.children[0].children;
  // 维护一个数组，保存每个子节点的id和高度，代替children进行增删改查操作
  // 由于dom节点数组不是数组，因此通过call进行遍历
  let data = Array.prototype.map.call(children, item => {
    console.log(item, '001')
    return {
      id: item.id,
      height: item.offsetHeight
    }
  })
  let domHeight = 0;
  // 预计算，根据每个节点的高度生成一个数组，遍历数组将对应dom保存到待打印dom中
  let printOrderArr = [];
  // 头部、个人信息、教育背景优先打印
  for (let i = 0; i < 3; i++) {
    printOrderArr.push({
      id: data[i].id,
      isEmpty: false
    })
    domHeight += data[i].height
  }
  data.splice(0, 3);
  // 遍历所有子节点
  while (data.length) {
    // 每一次处理第一个节点
    const nodeHeight = data[0].height;
    // 判断当前节点是否能加入到数组中
    if ((domHeight + nodeHeight) < this.pageHeight) {
      printOrderArr.push({
        id: data[0].id,
        isEmpty: false
      })
      domHeight += nodeHeight;
      // 删除当前元素
      data.splice(0, 1);
    } else {
      // 当前页剩余空白高度
      const lastHeight = this.pageHeight - domHeight;
      // 保存可以填充空白的板块
      let node;
      for (let j = 0; j < data.length; j++) {
        if (data[j].height < lastHeight) {
          node = data[j];
          data.splice(j, 1);
        }
      }
      // 如果有其他模块可以填充剩余空白部分
      if (node) {
        printOrderArr.push({
          id: node.id,
          isEmpty: false
        });
        domHeight += node.height;
      } else {
        // 剩余所有模块都无法填充剩余的那块空白
        printOrderArr.push({
          height: lastHeight,
          isEmpty: true
        })
        // 当前页已经填充了空白，重置domHeight，开始下一页
        domHeight = 0;
      }
    }
  }
  // dom节点的容器
  let pdfDom = document.createElement("div");
  pdfDom.id = "pdf";
  // 循环加入到容器中
  for (let i = 0; i < printOrderArr.length; i++) {
    let node = printOrderArr[i];
    // 判断是否是填充的空白div
    if (!node.isEmpty) {
      // 通过id获取到子节点中对应节点，通过遍历查找也是可以的
      let dom = Array.prototype.filter.call(children, item => item.id == node.id)[0];
      pdfDom.appendChild(dom.cloneNode(true));
    } else {
      let empty = document.createElement("div");
      empty.className = "empty";
      empty.style.height = node.height + 'px';
      pdfDom.appendChild(empty);
    }
  }
  let container = document.getElementsByClassName("resume-container")[0];
  container.appendChild(pdfDom);
  getPdf("pdf", "测试打印");
  container.removeChild(pdfDom)
}
