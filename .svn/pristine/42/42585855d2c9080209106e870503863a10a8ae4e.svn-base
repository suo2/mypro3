var twaverUtil = {
	//左边配置图元
	twaverAccordionPane: function (){
		this.init();
	},
	// 画布
	twaverNetwork: function (box){
		twaverUtil.twaverNetwork.superClass.constructor.call(this, box);
		this.init();
	},
	// 自定义图元
	twaverNode: function (obj){
		var id;
		if(obj) {
			id = obj.id;
		}
		twaverUtil.twaverNode.superClass.constructor.call(this, id);
		if(obj) {
			this.setImage(obj.image);
			this.setLocation(obj.location);
			this.setClient('nodetype', obj.nodetype);
		}
	},
	// 属性栏
	propertySheet: function(dataBox) {
		twaverUtil.propertySheet.superClass.constructor.call(this, dataBox);
		this.setBorderColor("none");
		this.setRowHeight(35);
		this.setSelectColor("none"); 
		this.setCategorizable(false);
	},
	getTwaverNode: function (obj, centerLocation, host){
		var node = new twaverUtil.twaverNode({
			image: obj.image,
			location: centerLocation,
			nodetype: obj.type
		});
		if(host) {
			node.setHost(host);
		}
		if (obj.width && obj.height) {
			node.setSize(obj.width, obj.height);
		}
		node.setCenterLocation(centerLocation);
		node.setName(obj.label);
		return node;
	},
	addDefaultNode: function (box, node){
		box.add(node);
	},
	// 添加图元
	addNode: function (box, obj, centerLocation, host){
		var node = twaverUtil.getTwaverNode(obj, centerLocation, host);
		box.add(node);
		box.getSelectionModel().setSelection(node);
		return node;
	},
	// 注册图片
	registerNormalImage: function(name, url, svg, network){
		var image = new Image();
		image.src = url;
		image.onload = function() {
			twaver.Util.registerImage(name, image, image.width, image.height, svg);
			image.onload = null;
			network.invalidateElementUIs();
		};
	},
	// 初始化右键菜单
	initPopupMenu: function (network, menuIsVisible, menuOnAction){
		network.popupMenu = new twaver.controls.PopupMenu(network);
		
		// 菜单显示前的回调函数
		network.popupMenu.onMenuShowing = function (e){
            twaverUtil.lastPoint = network.getLogicalPoint(e);
            return true;
        }
		
		// 设置右键菜单是否可见
        network.popupMenu.isEnabled = function (menuItem){
			return true;
		}
        
		// 过滤右键菜单
        network.popupMenu.isVisible = menuIsVisible;
        
        // 右键菜单动作
        network.popupMenu.onAction = menuOnAction;
	},
	selectNodes: new Array(),	//框选的图元
	lastPoint: {x:0, y:0},	//最后选择图元坐标
	copyPoint: {x:0, y:0},	// 复制鼠标图元坐标
	newTwaverNode: function (oldNode){
		// 复制图元
		var newNode = new twaverUtil.twaverNode({
			image: oldNode.getImage(),
			location: oldNode.getLocation(),
			nodetype: oldNode.getClient('nodetype')
		});
		newNode.setStyle('label.color', oldNode.getStyle('label.color'));
		newNode.setStyle('label.position', oldNode.getStyle('label.position'));
		newNode.setStyle('label.xoffset', oldNode.getStyle('label.xoffset'));
		newNode.setStyle('label.yoffset', oldNode.getStyle('label.yoffset'));
		newNode.setStyle('label.font', oldNode.getStyle('label.font'));
		newNode.setWidth(oldNode.getWidth());
		newNode.setHeight(oldNode.getHeight());
		newNode.setAngle(oldNode.getAngle());
		return newNode;
	},
	/**
	 * @param box 节点
	 * @param propertyName 属性key
	 * @param category 类别
	 * @param name 中文名称
	 */
	addStyleProperty: function (box, propertyName, category, name){
		// 样式属性
		return twaverUtil.addProperty(box, propertyName, category, name, 'style');
	},
	addClientProperty: function (box, propertyName, category, name){
		// 自定义的属性
		return twaverUtil.addProperty(box, propertyName, category, name, 'client');
	},
	addAccessorProperty: function (box, propertyName, category, name){
		// get/set方法的属性
		return twaverUtil.addProperty(box, propertyName, category, name, 'accessor');
	},
	addProperty: function (box, propertyName, category, name, proprtyType){
		var property = new twaver.Property();
		property.setCategoryName(category);
		property.setName(name);
		property.setEditable(true);
		property.setPropertyType(proprtyType);
		property.setPropertyName(propertyName);
		var valueType;
		if (proprtyType === 'style') {
			valueType = twaver.SerializationSettings.getStyleType(propertyName);
		} else if (proprtyType === 'client') {
		    valueType = twaver.SerializationSettings.getClientType(propertyName);
		} else {
		    valueType = twaver.SerializationSettings.getPropertyType(propertyName);
		}
		if (valueType) {
		    property.setValueType(valueType);
		}
		box.add(property);
		return property;
	},
	registerImages: function(network){
		var images = twaverResources.imageResources;
		for(var i in images) {
			var imagePath = images[i];
			var ifSvg = false;
			if(imagePath.endWith('.svg')) {
				ifSvg = true;
			}
			twaverUtil.registerNormalImage(i, imagePath, ifSvg, network);
		}
		twaverResources.registerSVGImage();//注册矢量图
	},
	getOrientation: function (){
		return ["东", "南", "西", "北"];
	},
	getTextSize: function(){
		return [12, 14, 16, 18, 20, 22, 24];
	},
	configurationInput: {}
};