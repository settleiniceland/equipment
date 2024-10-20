package cn.newness.imip.module.system.api.equip;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.newness.imip.module.infra.api.file.FileApi;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author machuran
 * @date 8/30/2024
 * @time 10:12 AM
 * @Description
 */
@Service
public class EquipProfileQrCodeApiImpl implements EquipProfileQrCodeApi {
    @Value("${temporary.directory}")
    private String TEMP_DIR;
    @Resource
    private FileApi fileApi;
    @Override
    public String createAndUploadQrCode(String equipProfileId,String equipName,String equipCode) {
        File qrcodefile = FileUtil.file(TEMP_DIR+"/"+equipProfileId+".png");
        QrCodeUtil.generate(equipProfileId,500,500,qrcodefile);
        String qrUrl = "";
        try (FileInputStream fis = new FileInputStream(qrcodefile)) {
            byte[] qrFileBytes = new byte[(int) qrcodefile.length()];
            fis.read(qrFileBytes);
            qrUrl = fileApi.createFile(equipName + equipCode + "二维码文件", equipCode + "/" + UUID.randomUUID().toString().replace("-","") +".png", qrFileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            qrcodefile.delete();
        }
        return qrUrl;
    }
}
