package cn.newness.imip.module.property.enums;

/**
 * @author machuran
 * @date 9/29/2024
 * @time 9:11 AM
 * @Description
 */
public interface PropertyLogRecordConstants {
    /*通用操作*/
    String COMMON_ADD = "新增操作";
    String COMMON_UPDATE = "修改操作";
    String COMMON_DEL = "删除操作";
    String COMMON_BIZNO = "1";
    /*生产厂家*/
    String MANUFACTURER = "生产厂家模块";
    //新增
    String MANUFACTURER_ADD_TYPE = "新增了生产厂家【{{#createReqVO.name}}】，编码【{{#createReqVO.code}}】";
    String MANUFACTURER_ADD_TYPE_FAIL = "新增生产厂家【{{#createReqVO.name}}】失败";
    //修改
    String MANUFACTURER_UPDATE_TYPE = "更新了生产厂家【{{#oldManufacturer.name}}】: \n{_DIFF{#updateReqVO}};\n"+
            "{{#oldManufacturer.status != #updateReqVO.status ? '《厂家状态:0正常,1禁用》' : ''}}";
    String MANUFACTURER_UPDATE_TYPE_FAIL = "更新生产厂家【{{#oldManufacturer.name}}】失败";
    //删除
    String MANUFACTURER_DEL_TYPE = "删除了生产厂家【{{#oldManufacturer.name}}】，编码【{{#oldManufacturer.code}}】";
    String MANUFACTURER_DEL_TYPE_FAIL = "删除生产厂家【{{#oldManufacturer.name}}】失败";

    /*设备存放位置*/
    String INSTALLLOCATION = "设备安装位置模块";
    //新增
    String INSTALLLOCATION_ADD_TYPE = "新增了设备安装位置【{{#createReqVO.name}}】，编码【{{#createReqVO.code}}】";
    String INSTALLLOCATION_ADD_TYPE_FAIL = "新增设备安装位置【{{#createReqVO.name}}】失败";
    //修改
    String INSTALLLOCATION_UPDATE_TYPE = "更新了设备安装位置【{{#oldInstalllocation.name}}】: \n{_DIFF{#updateReqVO}};\n";
    String INSTALLLOCATION_UPDATE_TYPE_FAIL = "更新设备安装位置【{{#oldInstalllocation.name}}】失败";
    //删除
    String INSTALLLOCATION_DEL_TYPE = "删除了设备安装位置【{{#oldInstalllocation.name}}】，编码【{{#oldInstalllocation.code}}】";
    String INSTALLLOCATION_DEL_TYPE_FAIL = "删除设备安装位置【{{#oldInstalllocation.name}}】失败";

    /*设备类别*/
    String EQUIPTYPE = "设备类别模块";
    //新增
    String EQUIPTYPE_ADD_TYPE = "新增了设备类别【{{#createReqVO.name}}】，编码【{{#createReqVO.code}}】";
    String EQUIPTYPE_ADD_TYPE_FAIL = "新增设备类别【{{#createReqVO.name}}】失败";
    //修改
    String EQUIPTYPE_UPDATE_TYPE = "修改了设备类别【{{#oldEquiptype.name}}】: \n{_DIFF{#updateReqVO}};\n";
    String EQUIPTYPE_UPDATE_TYPE_FAIL = "修改设备类别【{{#oldEquiptype.name}}】失败";
    //删除
    String EQUIPTYPE_DEL_TYPE = "删除了设备类别【{{#oldEquiptype.name}}】，编码【{{#oldEquiptype.code}}】";
    String EQUIPTYPE_DEL_TYPE_FAIL = "删除设备类别【{{#oldEquiptype.name}}】失败";

    /*设备框架*/
    String EQUIPFRAME = "设备框架模板";
    //新增
    String EQUIPFRAME_ADD_TYPE = "新增了设备框架【{{#createReqVO.equipName}}】";
    String EQUIPFRAME_ADD_TYPE_FAIL = "新增设备框架【{{#createReqVO.equipName}}】失败";
    //修改
    String EQUIPFRAME_UPDATE_TYPE = "修改了设备框架【{{#oldEquip.equipName}}】: \n{_DIFF{#updateReqVO}};\n";
    String EQUIPFRAME_UPDATE_TYPE_FAIL = "修改设备框架【{{#oldEquip.equipName}}】失败";
    //删除
    String EQUIPFRAME_DEL_TYPE = "删除了设备框架【{{#oldEquip.equipName}}】";
    String EQUIPFRAME_DEL_TYPE_FAIL = "删除设备框架【{{#oldEquip.equipName}}】失败";

    /*设备档案*/
    String EQUIPPROFILE = "设备档案模板";
    //新增
    String EQUIPPROFILE_ADD_TYPE = "新增了设备档案【{{#createReqVO.equipName}}】，编码【{{#createReqVO.code}}】";
    String EQUIPPROFILE_ADD_TYPE_FAIL = "新增设备档案【{{#createReqVO.equipName}}】失败";
    //修改
    String EQUIPPROFILE_UPDATE_TYPE = "更新了设备档案【{{#oldEquipmentprofile.equipName}}{{#oldEquipmentprofile.code}}】：" +
            "\n{_DIFF{#updateReqVO}};\n";
    String EQUIPPROFILE_UPDATE_TYPE_FAIL = "更新设备档案【{{#oldEquipmentprofile.equipName}}{{#oldEquipmentprofile.code}}】失败";
    String EQUIPPROFILE_STATUS_UPDATE_TYPE = "设备档案【{{#oldStatusEquipmentprofile.equipName}}{{#oldStatusEquipmentprofile.code}}】更新了状态: " +
            "{{#newStatusEquipmentprofile.status1 != #oldStatusEquipmentprofile.status1 ? '启停状态由【'+#oldStatusEquipmentprofile.status1+'】变为了【'+#newStatusEquipmentprofile.status1+'】《启停状态: 1开机, 2停机》' : ''}}"+
            "{{#newStatusEquipmentprofile.status2 != #oldStatusEquipmentprofile.status2 ? '其他状态由【'+#oldStatusEquipmentprofile.status2+'】变为了【'+#newStatusEquipmentprofile.status2+'】《其他状态: 3异动中, 4异动完毕, 5回国返修中, 6回国返修完毕, 7报废》' : ''}}";
    String EQUIPPROFILE_STATUS_UPDATE_TYPE_FAIL = "设备档案【{{#oldStatusEquipmentprofile.equipName}}{{#oldStatusEquipmentprofile.code}}】状态更新失败";
    //删除
    String EQUIPPROFILE_DEL_TYPE = "删除了设备档案【{{#oldEquipmentprofile.equipName}}{{#oldEquipmentprofile.code}}】";
    String EQUIPPROFILE_DEL_TYPE_FAIL = "删除设备档案【{{#oldEquipmentprofile.equipName}}{{#oldEquipmentprofile.code}}】失败";
}
