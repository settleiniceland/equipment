package cn.newness.imip.module.property.api;

/**
 * @author machuran
 * @date 2024.11.19
 * @time 下午 01:50
 * @Description
 */
public interface EquipApi {
    /**
    * 根据设备名获取其全层级设备名
    *
    * @param [equipId]
    * @author machuran
    * @date 2024.11.19
    * @Return java.lang.String
    */
    String getAllLayerEquipName(String equipId);
}
