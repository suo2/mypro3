// 组态资源
var twaverResources = {
	setClientPropertyType: function (setting){
		setting.isImageSerializable = false;
		// 设置自定义client属性
		setting.setClientType("nodetype", "cdata");
		//逆变器型号
		setting.setClientType("modelVersion", "cdata");
		setting.setClientType("orientation", "cdata");
		setting.setClientType("dipangle", "cdata");
		setting.setClientType("directionangle", "cdata");
		setting.setPropertyType("angle", "number");
		//图元是否绑定设备
		setting.setClientType("isBindDev","cdata");
		//bindNode 设备树节点id nodeId
		setting.setClientType("bindNode","cdata");
		//设备esn
		setting.setClientType("esnCode","cdata");
		//设备id
		setting.setClientType("devId","cdata");
		//设备名称
		setting.setClientType("devName","cdata");
		//设备类型id
		setting.setClientType("devTypeId","cdata");
		//父设备id
		setting.setClientType("parentDevId","cdata");
		setting.setPropertyType("name2", "string");
		//组件背景颜色
		setting.setClientType("assemblyBgColor","cdata");
		//组件颜色
		setting.setClientType("assemblyColor","cdata");
		//优化器颜色
		setting.setClientType("assemblyOptColor","cdata");
		//倾角
		setting.setClientType("dipangle","cdata");
		//方位角
		setting.setClientType("directionangle","cdata");
		// 组件name1
		setting.setClientType("assemblyText1","cdata");
		// 组件name2/发电量
		setting.setClientType("assemblyText2","cdata");
		setting.setClientType("assemblyUnitText", "cdata");
		//文字大小
		setting.setClientType("textSize","cdata");
		// 图元旋转前的坐标
		setting.setClientType("oldLocation", "cdata");
		// 多图元中心坐标
		setting.setClientType("oldCenterLocation", "cdata");
		//组件遮罩层颜色
		setting.setClientType("layerColor", "cdata");
		//组件遮罩层透明度
		setting.setClientType("layerAlpha", "cdata");
		//优化器序列号
		setting.setClientType("sequence", "cdata");
		//逆变器颜色
		setting.setClientType("inverterColor", "cdata");
		//逆变器name2
		setting.setClientType("inverterText2","cdata");
		//逆变器name1
		setting.setClientType("inverterText","cdata");
		//逆变器功率
		setting.setClientType("inverterPowerText","cdata");
		//逆变器功率单位
		setting.setClientType("inverterUnitText", "cdata");
		// 多图元选中组合
		setting.setClientType("selectGroup", "cdata");
	},
	// 注册自定义图片，key图片名称，value图片地址
	imageResources: {
		'inverterTemp': 'js/plugins/twaver/images/topoInverter.png',
		'assemblys': 'js/plugins/twaver/images/topoAssembly.png',
		'inverters': 'js/plugins/twaver/images/topoInverter.png',
		'inverterDisconnected':'js/plugins/twaver/images/icons/inverterDisconnected.png',
		'assemblyWithOptHori':'js/plugins/twaver/images/icons/string_opt_hori.png'
	},
	//注册矢量图
	registerSVGImage: function (){
		//逆变器
		twaver.Util.registerImage('inverter', {
			w: 130,
			h: 130,
			v: [
			    {//逆变器底
			    	shape: 'vector',
			    	name: 'inv',
			    	x: -57,
			    	y: -59
			    },
			    {//逆变器半圆
			    	shape: 'vector',
			    	name: 'invCircle',
			    	x: -25,
			    	y: 31.5
			    },
			    {//逆变器华为logo
			    	shape: 'vector',
			    	name: 'invLogo',
			    	x: -12.5,
			    	y: 36
			    },
			    {
					shape: 'text',
					text: '<%= getClient("inverterText") %>',
					y:-13,
					fill:'#000000',
					font:'20px Calibri bold'
				},
			    {
					shape: 'text',
					text: '<%= getClient("inverterText2") %>',
					y:66,
					fill:'#000000',
					font:'16px Calibri bold'
				},
                {
                    shape: 'text',
                    text: '<%= getClient("inverterUnitText") %>',
                    y: 6,
                    fill: '#FFFFFF',
                    font: '20px Calibri bold'
                },
				{
					shape: 'text',
					text: '<%= getClient("inverterPowerText") %>>',
					y:-20,
					fill:'#FFFFFF',
					font:'25px Calibri bold'
				},
                {//逆变器透明遮罩层
                    shape: 'vector',
                    name: 'inverterLayer',
                    x: -57,
                    y: -59
                }
			]
		});
		//逆变器底
		twaver.Util.registerImage('inv', {
			w: 114,
			h: 114,
			v: [
			    {
			    	shape: 'path',
			    	data: 'M 114.407 103.523 C 114.407 110.21 108.987 115.629 102.302 115.629 L 12.514 115.629 C 5.827003 115.629 0.4079971 110.209 0.4079971 103.523 L 0.4079971 13.73499 C 0.4079971 7.048996 5.827995 1.62999 12.514 1.62999 L 102.302 1.62999 C 108.988 1.62999 114.407 7.049988 114.407 13.73499 L 114.407 103.523 Z',
			    	fill: '<%= getClient("inverterColor") %>'
			    }
			]
		});
		//逆变器半圆
		twaver.Util.registerImage('invCircle', {
			w: 50,
			h: 25,
			v: [
			    {
			    	shape: 'path',
			    	data: 'M0,25C0,11.193,11.193,0,25,0C38.807,0,50,11.193,50,25',
			    	fill: '#040000'
			    }
			]
		});
		//逆变器华为logo
		twaver.Util.registerImage('invLogo', {
			w: 25,
			h: 20,
			v: [
			    {
			    	shape: 'path',
			    	data: 'M 10.763 14.821 C 10.763 14.821 10.807 14.786 10.784 14.727 L 10.784 14.727 C 7.848 8.254 3.829 3.348 3.829 3.348 C 3.829 3.348 1.643 5.44 1.798 7.537 C 1.881 9.123 3.065 10.063 3.065 10.063 C 4.972 11.936 9.587 14.302 10.661 14.839 C 10.675 14.845 10.73 14.863 10.763 14.821 ZM 10.049 16.423 C 10.03 16.354 9.951 16.354 9.951 16.354 L 9.951 16.352 L 2.266 16.623 C 3.1 18.121 4.503 19.285 5.965 18.928 C 6.975 18.674 9.26 17.067 10.014 16.522 L 10.012 16.521 C 10.07 16.467 10.049 16.423 10.049 16.423 ZM 10.166 15.727 C 10.203 15.667 10.137 15.613 10.137 15.613 L 10.137 15.611 C 6.762 13.315 0.22 9.789 0.22 9.789 C -0.374 11.646 0.427 13.143 0.427 13.143 C 1.261 14.919 2.853 15.458 2.853 15.458 C 3.588 15.763 4.323 15.783 4.323 15.783 C 4.438 15.804 8.893 15.785 10.087 15.78 C 10.137 15.78 10.166 15.727 10.166 15.727 ZM 10.675 0 C 10.341 0.03 9.438 0.236 9.438 0.236 C 7.404 0.766 6.922999 2.629 6.922999 2.629 C 6.552 3.801 6.933 5.087 6.933 5.087 C 7.612 8.127 10.954 13.121 11.672 14.17 C 11.723 14.221 11.763 14.202 11.763 14.202 C 11.841 14.181 11.835 14.105 11.835 14.105 L 11.835 14.106 C 12.941 2.956 10.675 0 10.675 0 ZM 13.219 14.2 C 13.294 14.229 13.331 14.158 13.331 14.158 L 13.331 14.158 C 14.067 13.083 17.391 8.116 18.067 5.088 C 18.067 5.088 18.432 3.622 18.079 2.63 C 18.079 2.63 17.576 0.7390003 15.539 0.2390003 C 15.539 0.2390003 14.952 0.09000032 14.329 0.001000315 C 14.329 0.001000315 12.052 2.958 13.159 14.114 L 13.161 14.114 C 13.169 14.185 13.219 14.2 13.219 14.2 ZM 15.039 16.359 C 15.039 16.359 14.971 16.368 14.953 16.419 C 14.953 16.419 14.935 16.488 14.982 16.523 L 14.98 16.524 C 15.716 17.056 17.946 18.628 19.021 18.933 C 19.021 18.933 21.007 19.617 22.732 16.626 L 15.039 16.356 L 15.039 16.359 ZM 24.779 9.773 C 24.779 9.773 18.246 13.309 14.869 15.606 L 14.87 15.607 C 14.87 15.607 14.808 15.648 14.83 15.72 C 14.83 15.72 14.862 15.779 14.909 15.779 L 14.909 15.78 C 16.118 15.782 20.695 15.787 20.81 15.765 C 20.81 15.765 21.403 15.741 22.134 15.458 C 22.134 15.458 23.762 14.935 24.607 13.07 C 24.607 13.071 25.362 11.55 24.779 9.773 ZM 14.251 14.821 C 14.251 14.821 14.305 14.861 14.355 14.828 L 14.355 14.829 C 15.457 14.278 20.038 11.927 21.934 10.063 C 21.934 10.063 23.135 9.089001 23.199 7.527 C 23.338 5.356 21.172 3.348001 21.172 3.348001 C 21.172 3.348001 17.165 8.239 14.229 14.698 L 14.231 14.697 C 14.23 14.697 14.195 14.773 14.251 14.821 Z',
			    	fill: '<%= getClient("inverterColor") %>'
			    }
			]
		});
		//逆变器透明遮罩层
		twaver.Util.registerImage('inverterLayer', {
			w: 114,
			h: 114,
			v: [
			    {
			    	shape: 'path',
			    	data: 'M 114.407 103.523 C 114.407 110.21 108.987 115.629 102.302 115.629 L 12.514 115.629 C 5.827003 115.629 0.4079971 110.209 0.4079971 103.523 L 0.4079971 13.73499 C 0.4079971 7.048996 5.827995 1.62999 12.514 1.62999 L 102.302 1.62999 C 108.988 1.62999 114.407 7.049988 114.407 13.73499 L 114.407 103.523 Z',
			    	fill: '<%= getClient("layerColor") %>',
			    	alpha: '<%= getClient("layerAlpha") %>'
			    }
			]
		});
		//组件纵向
		twaver.Util.registerImage('assembly', {
			w: 77,
			h: 114,
			lineWidth: 1,
			lineColor: '<%= getClient("assemblyColor") %>',
			v: [
				{
					shape: 'vector',
					name: 'assBg',
					x: 0,
					y: -9
				},
				{
					shape: 'vector',
					name: 'ass',
					x: 0,
					y: -9
				},
				{
					shape: 'vector',
					name: 'textArea',
					x: 0,
					y: 47.5
				},
                {
					shape: 'text',
					text: '<%= getClient("assemblyText1") %>',
					y:48,
					fill:'#FFFFFF',
					font:'16px Calibri bold'
				},
				{
					shape: 'text',
					text: '<%= getClient("assemblyText2") %>',
					y:-13,
					fill:'#FFFFFF',
					font:'14px Calibri bold'
				},
                {
                    shape: 'text',
                    text: '<%= getClient("assemblyUnitText") %>',
                    y: 5,
                    fill: '#FFFFFF',
                    font: '16px Calibri bold'
                }
			]
		});
		//组件背景
		twaver.Util.registerImage('assBg', {
			lineWidth: 1,
			lineColor: '<%= getClient("assemblyColor") %>',
			v: [
				{
					shape: 'path',
					data: 'M-38 -48L38 -48L38 47L-38 47',
					lineColor: '<%= getClient("assemblyBgColor") %>',
					fill: '<%= getClient("assemblyBgColor") %>'
				}
			]
		});
		//组件
		twaver.Util.registerImage('ass', {
			lineWidth: 1,
			lineColor: '<%= getClient("assemblyColor") %>',
			v: [
				{
					shape: 'path',
					data: 'M-37 -47L-20 -47L-20 -30L-37 -30M-37 -28L-20 -28L-20 -11L-37 -11M-37 -9L-20 -9L-20 8L-37 8M-37 10L-20 10L-20 27L-37 27M-37 29L-20 29L-20 46L-37 46M-18 -47L-1 -47L-1 -30L-18 -30M-18 -28L-1 -28L-1 -11L-18 -11M-18 -9L-1 -9L-1 8L-18 8M-18 10L-1 10L-1 27L-18 27M-18 29L-1 29L-1 46L-18 46M1 -47L18 -47L18 -30L1 -30M1 -28L18 -28L18 -11L1 -11M1 -9L18 -9L18 8L1 8M1 10L18 10L18 27L1 27M1 29L18 29L18 46L1 46M20 -47L37 -47L37 -30L20 -30M20 -28L37 -28L37 -11L20 -11M20 -9L37 -9L37 8L20 8M20 10L37 10L37 27L20 27M20 29L37 29L37 46L20 46',
					lineColor: '<%= getClient("assemblyColor") %>',
					fill: '<%= getClient("assemblyColor") %>'
				}
			]
		});
		//黑色文本区
		twaver.Util.registerImage('textArea', {
			w: 76.5,
			h: 18,
			lineColor: '#000000',
			v: [
			    {
			    	shape: 'line',
			    	x1: -38,
			    	y1: 0,
			    	x2: 38.5,
			    	y2: 0,
			    	lineWidth: 18
			    }
			]
		});
		//组件透明遮罩层
		twaver.Util.registerImage('assemblyLayer', {
			w: 76.5,
			h: 114.5,
			lineColor: '<%= getClient("layerColor") %>',
			v: [
			    {
					shape: 'line',
			    	x1: -38,
			    	y1: 0,
			    	x2: 38.5,
			    	y2: 0,
			    	alpha: '<%= getClient("layerAlpha") %>',
			    	lineWidth: 114.5
			    }
			]
		});
		//组件横向
		twaver.Util.registerImage('assemblyHori', {
			w: 96,
			h: 77,
			lineWidth: 1,
			lineColor: '<%= getClient("assemblyColor") %>',
			v: [
			    {
			    	shape: 'path',
			    	data: 'M-47 -38L48 -38L48 38L-47 38',
			    	lineColor: '<%= getClient("assemblyBgColor") %>',
			    	fill: '<%= getClient("assemblyBgColor") %>'
			    },
			    {
			    	shape: 'path',
			    	data: 'M-46 -37L-29 -37L-29 -20L-46 -20M-27 -37L-10 -37L-10 -20L-27 -20M-46 -18L-29 -18L-29 -1L-46 -1M-27 -18L-10 -18L-10 -1L-27 -1M-8 -37L9 -37L9 -20L-8 -20M-8 -18L9 -18L9 -1L-8 -1M11 -37L28 -37L28 -20L11 -20M30 -37L47 -37L47 -20L30 -20M11 -18L28 -18L28 -1L11 -1M30 -18L47 -18L47 -1L30 -1M-46 1L-29 1L-29 18L-46 18M-27 1L-10 1L-10 18L-27 18M-8 1L9 1L9 18L-8 18M11 1L28 1L28 18L11 18M30 1L47 1L47 18L30 18M-46 20L-29 20L-29 37L-46 37M-27 20L-10 20L-10 37L-27 37M-8 20L9 20L9 37L-8 37M11 20L28 20L28 37L11 37M30 20L47 20L47 37L30 37',
			    	lineColor: '<%= getClient("assemblyColor") %>',
			    	fill: '<%= getClient("assemblyColor") %>'
			    }
			]
		});
		//优化器
		twaver.Util.registerImage('optimizer', {
			v: [
				{
					shape: 'path',
					data: 'M17,0H1C0.5,0,0,0.5,0,1v10c0,0.5,0.5,1,1,1h1v2h3v-2h8v2h3v-2h1c0.5,0,1-0.5,1-1V1C18,0.5,17.5,0,17,0z&#xD;&#xA;		 M8.6,10.6C8.5,10.7,8.1,11,8,11c-0.2,0.1-0.5-0.1-0.6-0.4L8.6,10.6L8.6,10.6C8.6,10.6,8.6,10.6,8.6,10.6&#xD;&#xA;		C8.6,10.6,8.6,10.6,8.6,10.6L8.6,10.6z M8.6,10.5c-0.2,0-0.9,0-0.9,0c0,0-0.1,0-0.2-0.1c0,0-0.3-0.1-0.4-0.4c0,0-0.1-0.2,0-0.5&#xD;&#xA;		C7,9.6,8.1,10.1,8.6,10.5L8.6,10.5C8.6,10.5,8.6,10.5,8.6,10.5C8.6,10.5,8.6,10.5,8.6,10.5z M8.7,10.3C8.7,10.4,8.7,10.4,8.7,10.3&#xD;&#xA;		C8.7,10.4,8.7,10.4,8.7,10.3c-0.2-0.1-0.9-0.4-1.2-0.7c0,0-0.2-0.1-0.2-0.4c0-0.3,0.3-0.7,0.3-0.7S8.3,9.3,8.7,10.3L8.7,10.3z&#xD;&#xA;		 M8.9,10.3L8.9,10.3C8.9,10.3,8.9,10.3,8.9,10.3C8.9,10.3,8.9,10.3,8.9,10.3c-0.1-0.2-0.7-0.9-0.8-1.4c0,0-0.1-0.2,0-0.4&#xD;&#xA;		c0,0,0.1-0.3,0.4-0.4c0,0,0.1,0,0.2,0C8.7,8,9.1,8.5,8.9,10.3z M9.1,10.3C9.1,10.3,9.1,10.3,9.1,10.3C9.1,10.3,9.1,10.3,9.1,10.3&#xD;&#xA;		L9.1,10.3C8.9,8.5,9.3,8,9.3,8c0.1,0,0.2,0,0.2,0c0.3,0.1,0.4,0.4,0.4,0.4c0.1,0.2,0,0.4,0,0.4C9.8,9.3,9.3,10.1,9.1,10.3L9.1,10.3&#xD;&#xA;		z M9.3,10.4C9.3,10.4,9.3,10.3,9.3,10.4L9.3,10.4c0.5-1,1.1-1.8,1.1-1.8s0.3,0.3,0.3,0.7c0,0.2-0.2,0.4-0.2,0.4&#xD;&#xA;		C10.2,9.9,9.5,10.3,9.3,10.4L9.3,10.4C9.3,10.4,9.3,10.4,9.3,10.4z M10,11c-0.2,0-0.5-0.3-0.6-0.4l0,0c0,0,0,0,0,0c0,0,0,0,0,0l0,0&#xD;&#xA;		l1.2,0C10.4,11.1,10,11,10,11z M10.9,10.1c-0.1,0.3-0.4,0.4-0.4,0.4c-0.1,0-0.2,0-0.2,0c0,0-0.8,0-0.9,0v0c0,0,0,0,0,0c0,0,0,0,0,0&#xD;&#xA;		l0,0C9.9,10.1,11,9.6,11,9.6C11.1,9.8,10.9,10.1,10.9,10.1z',
					lineColor: '<%= getClient("assemblyOptColor") %>',
					fill: '<%= getClient("assemblyOptColor") %>'
				}
			]
		});
		//华为logo
		twaver.Util.registerImage('hwLogo', {
			lineWidth: 0.01,
			v: [
			    {
			    	shape: 'path',
					data: 'M 7.299994 9.199979 C 7.299994 9.499979 7.499994 9.599978 7.499994 9.599978 C 7.799994 9.899979 8.499993 10.29998 8.699993 10.39998 C 8.699993 10.39998 8.699993 10.39998 8.699993 10.39998 C 8.699993 10.39998 8.699993 10.39998 8.699993 10.39998 L 8.699993 10.39998 C 8.199993 9.399979 7.599994 8.599978 7.599994 8.599978 C 7.599994 8.599978 7.299994 8.899979 7.299994 9.199979 ZM 8.599994 10.59998 L 8.599994 10.59998 L 7.399994 10.59998 C 7.499994 10.89998 7.699994 11.09998 7.999994 10.99998 C 8.099994 10.99998 8.499993 10.69998 8.599994 10.59998 L 8.599994 10.59998 C 8.599994 10.59998 8.599994 10.59998 8.599994 10.59998 C 8.599994 10.59998 8.599994 10.59998 8.599994 10.59998 ZM 8.599994 10.49998 C 8.099994 10.09998 6.999994 9.599979 6.999994 9.599979 C 6.899994 9.89998 6.999994 10.09998 6.999994 10.09998 C 7.099994 10.39998 7.399994 10.49998 7.399994 10.49998 C 7.499994 10.49998 7.599994 10.59998 7.599994 10.59998 C 7.699994 10.49998 8.399993 10.49998 8.599994 10.49998 C 8.599994 10.49998 8.599994 10.49998 8.599994 10.49998 C 8.599994 10.49998 8.599994 10.49998 8.599994 10.49998 L 8.599994 10.49998 ZM 8.499993 8.099979 C 8.199993 8.099979 8.099994 8.399979 8.099994 8.399979 C 7.999994 8.599978 8.099994 8.799978 8.099994 8.799978 C 8.199994 9.299978 8.699994 10.09998 8.899994 10.19998 C 8.899994 10.19998 8.899994 10.19998 8.899994 10.19998 C 8.899994 10.19998 8.899994 10.19998 8.899994 10.19998 L 8.899994 10.19998 C 9.099994 8.499979 8.699993 7.999979 8.699993 7.999979 C 8.699993 7.999979 8.499993 8.099979 8.499993 8.099979 ZM 9.899993 8.399979 C 9.899993 8.399979 9.799993 8.099978 9.499993 7.999979 C 9.499993 7.999979 9.399993 7.999979 9.299994 7.999979 C 9.299994 7.999979 8.899994 8.499979 9.099994 10.19998 L 9.099994 10.19998 C 9.099994 10.19998 9.099994 10.19998 9.099994 10.19998 C 9.099994 10.19998 9.099994 10.19998 9.099994 10.19998 L 9.099994 10.19998 C 9.199994 9.999979 9.699994 9.199979 9.899994 8.799979 C 9.899993 8.799979 9.899993 8.599979 9.899993 8.399979 ZM 9.399993 10.59998 C 9.399993 10.59998 9.399993 10.59998 9.399993 10.59998 C 9.399993 10.59998 9.399993 10.59998 9.399993 10.59998 L 9.399993 10.59998 C 9.499993 10.69998 9.899993 10.99998 9.999993 10.99998 C 9.999993 10.99998 10.29999 11.09998 10.59999 10.59998 L 9.399993 10.59998 L 9.399993 10.59998 ZM 9.399993 10.49998 L 9.399993 10.49998 C 9.399993 10.49998 9.399993 10.49998 9.399993 10.49998 C 9.399993 10.49998 9.399993 10.49998 9.399993 10.49998 L 9.399993 10.49998 C 9.599993 10.49998 10.29999 10.49998 10.39999 10.49998 C 10.39999 10.49998 10.49999 10.49998 10.59999 10.49998 C 10.59999 10.49998 10.89999 10.39998 10.99999 10.09998 C 10.99999 10.09998 11.09999 9.89998 10.99999 9.599979 C 10.99999 9.599979 9.899993 10.09998 9.399993 10.49998 ZM 10.49999 9.599979 C 10.49999 9.599979 10.69999 9.39998 10.69999 9.19998 C 10.69999 8.89998 10.39999 8.49998 10.39999 8.49998 C 10.39999 8.49998 9.799993 9.29998 9.299993 10.29998 L 9.299993 10.29998 C 9.299993 10.29998 9.299993 10.29998 9.299993 10.29998 C 9.299993 10.29998 9.299993 10.29998 9.299993 10.29998 L 9.299993 10.29998 C 9.499993 10.29998 10.19999 9.899979 10.49999 9.599979 Z',
					lineColor: '#FFFFFF',
					fill: '#FFFFFF'
			    }
			]
		});
		//组件纵绑定优化器
		twaver.Util.registerImage('assemblyWithOpt', {
			w: 77,
			h: 114,
			lineWidth: 1,
			lineColor: '<%= getClient("assemblyColor") %>',
			v: [
				{
					shape: 'vector',
					name: 'assembly'
				},
				{//白圈
					shape: 'circle',
					cx: 23,
					cy: -42,
					r: 14,
					lineColor: '#FFFFFF',
					fill: 'white'
				},
				{//优化器
					shape: 'vector',
					name: 'optimizer',
					x: 14,
					y: -49
				},
				{//华为logo
					shape: 'vector',
					name: 'hwLogo',
					x: 14,
					y: -49
				},
                {//遮罩层
                    shape: 'vector',
                    name: 'assemblyLayer',
                    x: 0,
                    y: -0.5
                }
			]
		});
	},
	categoryJson: {
        categories: [{
            contents: [{
                icon: "js/plugins/twaver/images/icons/assembly.png",
                type: 'assembly',
                image: 'assembly'
            }, {
                icon: "js/plugins/twaver/images/icons/invernate.png",
                type: 'inverter',
                image: 'inverter'
            }]
        }],
        // 功能按钮
        configBtns: [{
            // 保存
            icon: 'js/plugins/twaver/images/icons/save.png',
            hover: 'js/plugins/twaver/images/icons/save_hover.png',
            click: 'save'
        }, {
            // 删除
            icon: 'js/plugins/twaver/images/icons/delete.png',
            hover: 'js/plugins/twaver/images/icons/delete_hover.png',
            click: 'clear'
        }, {
            // 放大
            icon: 'js/plugins/twaver/images/icons/enlarge.png',
            hover: 'js/plugins/twaver/images/icons/enlarge_hover.png',
            click: 'zoomIn'
        }, {
            // 缩小
            icon: 'js/plugins/twaver/images/icons/narrow.png',
            hover: 'js/plugins/twaver/images/icons/narrow_hover.png',
            click: 'zoomOut'
        }, {
            // 上对齐
            icon: 'js/plugins/twaver/images/icons/topAlign.png',
            hover: 'js/plugins/twaver/images/icons/topAlign_hover.png',
            click: 'setAlignT'
        }, {
            // 下对齐
            icon: 'js/plugins/twaver/images/icons/lowerAlign.png',
            hover: 'js/plugins/twaver/images/icons/lowerAlign_hover.png',
            click: 'setAlignB'
        }, {
            // 左对齐
            icon: 'js/plugins/twaver/images/icons/leftAlign.png',
            hover: 'js/plugins/twaver/images/icons/leftAlign_hover.png',
            click: 'setAlignL'
        }, {
            // 右对齐
            icon: 'js/plugins/twaver/images/icons/rightAlign.png',
            hover: 'js/plugins/twaver/images/icons/rightAlign_hover.png',
            click: 'setAlignR'
        }, {
            // 居中
            icon: 'js/plugins/twaver/images/icons/center.png',
            hover: 'js/plugins/twaver/images/icons/center_hover.png',
            click: 'topoCenter'
        }, {
            // 逆时针旋转
            icon: 'js/plugins/twaver/images/icons/counter.png',
            hover: 'js/plugins/twaver/images/icons/counter_hover.png',
            click: 'setAngleL'
        }, {
            // 顺时针旋转
            icon: 'js/plugins/twaver/images/icons/clockwise.png',
            hover: 'js/plugins/twaver/images/icons/clockwise_hover.png',
            click: 'setAngleR'
        }, {
            // 旋转
            id: 'inputAngleRotate',
            icon: 'js/plugins/twaver/images/icons/rotation.png',
            hover: 'js/plugins/twaver/images/icons/rotation_hover.png',
            input: 'number',
            click: 'setAngleInp'
        }, {
            // 回退
            icon: 'js/plugins/twaver/images/icons/regresses.png',
            hover: 'js/plugins/twaver/images/icons/regresses_hover.png',
            click: 'regresses'
        }]
    },
	validateLicense: function (){
		twaver.Util.validateLicense(
			"l=1.0\n"+
			"type=3\n"+
			"gis=0\n"+
			"3d=0\n"+
			"start=2015-05-22\n"+
			"licensee=Chengdu TD Tech Ltd.\n"+
			"licensedUser=1 USER\n"+
			"periodofValidity=PERMANENT\n"+
			"maintenanceandUpgrade=12 MONTH\n"+
			"buyer=Chengdu TD Tech Ltd.\n"+
			"signature=4fdb7472d59c99b2d12af2b6635807b462bfdc082d7bad65a6d675c080e17b5022bd0523b5a1a4a90b15ef8869864e9a360f7bd14fba390344e6e74074b293ae0341838c7e9830c947dd8a61ca901ce95ae1077b48284d3052fcd63ef4e72ee93ea688bac7093bbe8d3c075a502ec0e055b68aaf68a33aaf2448f7cc366038fc1cd5169ab69829bd649a529952cfbf02960af890e17eef9f5a0e8f5ad0c9bfb01cd3273ba9f929634d08c70a397a001bc553a2d94e242f9fc181642feecb5ce7466708e6af7f569c927b146718db7d9b31e550def5b1ad4d794c99b31b34cd252b4d2913ad685b2b6f95f8c23ca7382ee17fea28c806dc6d28ef293f91a4a0e6"
		);
	}
}