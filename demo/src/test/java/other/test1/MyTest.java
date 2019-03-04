package other.test1;


import com.common.mapper.JsonMapper;
import com.common.util.FileUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

public class MyTest {

//    private static int length = 44;
//    private static String filePath = System.getProperty("user.dir") + File.separator + "demo" + File.separator + "src" + File.separator + "test" +
//                                     File.separator + "java" + File.separator + "other" + File.separator + "test1" + File.separator + "map.txt";


    // 公司
//    private static String pathP = "C:\\Users\\zhoubin\\Pictures\\壁纸";
//    private static String pathA = "D:\\Workspaces\\temp\\rename\\a";
//    private static String pathB = "D:\\Workspaces\\temp\\rename\\b";
//    private static String pathImage = "D:\\Workspaces\\temp\\rename\\image";

    // 家里
//    private static String pathP = "E:\\Other\\图片\\壁纸";
//    private static String pathA = "E:\\Workspaces\\temp\\rename\\a";
//    private static String pathB = "E:\\Workspaces\\temp\\rename\\b";
//    private static String pathImage = "E:\\Workspaces\\temp\\rename\\image";


    private static String pathP;
    private static String pathA;
    private static String pathB;
    private static String pathImage;
    private static String mapTxtPath;

    static{
        System.out.println(System.getProperty("user.dir"));
        // 初始化路径
        boolean isCompany = Boolean.parseBoolean(PropertiesUtil.getConfig("isCompany"));
        if (isCompany) {
            pathP = PropertiesUtil.getConfig("company.pathP");
            pathA = PropertiesUtil.getConfig("company.pathA");
            pathB = PropertiesUtil.getConfig("company.pathB");
            pathImage = PropertiesUtil.getConfig("company.pathImage");
            mapTxtPath = PropertiesUtil.getConfig("company.map.txt.path");
        } else {
            pathP = PropertiesUtil.getConfig("home.pathP");
            pathA = PropertiesUtil.getConfig("home.pathA");
            pathB = PropertiesUtil.getConfig("home.pathB");
            pathImage = PropertiesUtil.getConfig("home.pathImage");
            mapTxtPath = PropertiesUtil.getConfig("home.map.txt.path");
        }
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        // 复制壁纸
        bzAll();

        // 将图片按照文件名排序
//		sortA();

        // 将歌曲重命名，按照 "-" 分割，歌手名字放前面，歌曲名称放后面
//        soundRename();
    }

    /**
     * 歌手名称 和 歌曲名称 互换位置
     * @throws IOException
     */
    public static void soundRename() throws IOException {
        File file = new File(pathA);
        File[] fileArray = file.listFiles();
        for (File f : fileArray) {
            String name = f.getName();
            // 后缀
            String suffix = name.substring(name.lastIndexOf("."));
            String[] str1 = name.split(suffix);
            String[] str2 = str1[0].split("-");

            String newName = str2[1] + " - " + str2[0] + suffix;
            System.out.println("旧名称[" + name + "]改成新名称[" + newName + "]");
            FileUtil.fileCopy(f, new File(pathB + File.separator + newName));
        }
    }


    /**
     * 将a文件夹中的图片按照名字排序，如果有空档的图片就将当前图片的名称按顺序修改
     * 比如 1.jpg, 2.jpg, 6.jpg, 8.jpg 修改成 1.jpg, 2.jpg, 3.jpg, 4.jpg
     */
    public static void sortA() {

        File file = new File(pathA);
        File[] fileArray = file.listFiles();
        List<File> list = Arrays.asList(fileArray);

        // 冒泡排序
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 1; j < list.size() - i; j++) {
                File a;

                String str1 = list.get(j - 1).getName();
                int name1 = Integer.parseInt(str1.substring(0, str1.lastIndexOf(".")));
                String str2 = list.get(j).getName();
                int name2 = Integer.parseInt(str2.substring(0, str2.lastIndexOf(".")));

                if (name1 > name2) {
                    a = list.get(j - 1);
                    list.set((j - 1), list.get(j));
                    list.set(j, a);
                }

            }
        }

        for (File file2 : list) {
            System.out.println(file2.getName());
        }

        int index = 0;
        // 判断是否有空出来的序号
        for (int i = 0; i < list.size(); i++) {

            if ((i + 1) < list.size()) {
                File f1 = list.get(i);
                File f2 = list.get(i + 1);

                int name1 = Integer.parseInt(f1.getName().substring(0, f1.getName().lastIndexOf(".")));
                int name2 = Integer.parseInt(f2.getName().substring(0, f2.getName().lastIndexOf(".")));

                int result = name2 - name1;

                // 大于你就说明中间有空白,就需要将后面的都加上这个结果
                if (result > 1) {
                    index += name1 - name2 + 1;
                }

                if (i == 0) {
                    f1.renameTo(new File(pathB + File.separator + f1.getName()));
                }
                // 将后面的文件都重命名
                f2.renameTo(new File(pathB + File.separator + (name2 + index) + ".jpg"));
                System.out.println(pathB + File.separator + (name2 + index) + ".jpg");
            }
        }

    }

    private static void bzAll() throws InterruptedException {

        FileUtil.deleteDirFile(pathA);
        FileUtil.deleteDirFile(pathB);
        FileUtil.deleteDirFile(pathImage);

        FileUtil.createDir(pathA);
        FileUtil.createDir(pathB);
        FileUtil.createDir(pathImage);


        step1(); // 1 将系统 Assets 文件夹下面的图片异动到 a 文件夹
        System.out.println("\n\n");
        Thread.sleep(2000);

        step2(); // 2 将a文件夹中的图片改名
        System.out.println("\n\n");
        Thread.sleep(2000);

        step3(); // 3 删除 宽不为 1960 高不为 1080 的图片
        System.out.println("\n\n");
        Thread.sleep(2000);

        step4(); // 4 对比壁纸和a文件夹中图片，将重复的移动到b文件夹
        System.out.println("\n\n");
        Thread.sleep(2000);

        step5(); // 5 将a文件夹中的图片移动到壁纸文件夹，同时移动到image文件夹之后删除a文件夹中的图片
        System.out.println("\n\n");
        Thread.sleep(2000);

        step6(); // 6 将壁纸文件夹中重复的图片移动到a文件夹中,并删除壁纸文件夹中名称靠后的图片

    }





    /**
     * 1 将系统文件夹里面的缓存图片复制到a文件夹
     * C:\Users\zhoubin\AppData\Local\Packages\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\LocalState\Assets
     */
    private static void step1() {
//        String path = "C:\\Users\\zhoubin\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets";
        String path = PropertiesUtil.getConfig("pathc");

        System.out.println("###########【1、移动 Assets 下面所有的文件到 a 文件夹】###########");
        System.out.println(String.format("Assets:[%s]", path));
        System.out.println(String.format("a:[%s]", pathA));
        System.out.println("开始");
        System.out.println(String.format("将 %s 文件夹下的图片移动到 %s 文件夹下", path, pathA));
        FileUtil.fileCopy(path, pathA);
        System.out.println("结束");
    }

    /**
     * 2 将a文件夹中的图片改名
     */
    private static void step2() {
        System.out.println("###########【2、修改 a 文件夹下文件的后缀名】###########");
        System.out.println(String.format("a:[%s]", pathA));
        System.out.println("开始");

        // 文件改名字
        File file = new File(pathA);
        File[] fileArr = file.listFiles();

        assert fileArr != null;
        for (int i = 0; i < fileArr.length; i++) {
            File f = fileArr[i];
            String name = (i + 1) + ".jpg";
            System.out.println(String.format("将 [%s] 文件修改名称为 [%s]", pathA, pathA + File.separator + name));
            f.renameTo(new File(pathA + File.separator + name));
        }
        System.out.println("结束");
    }

    /**
     * 3 删除 宽不为 1960 高不为 1080 的图片
     */
    private static void step3() {
        System.out.println("###########【3、删除 a 文件夹下 高不为 1080 宽不为为 1920 的图片】###########");
        System.out.println(String.format("a:[%s]", pathA));
        System.out.println("开始");
        File filea = new File(pathA);
        File[] fileArra = filea.listFiles();

        assert fileArra != null;
        for (File file : fileArra) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedImage sourceImg = ImageIO.read(fileInputStream);
                if (sourceImg == null) {
                    file.delete();
                } else {
                    String name = file.getName();
                    int width = sourceImg.getWidth();
                    int height = sourceImg.getHeight();
                    System.out.print(String.format("名称: %s, 宽: %s, 高: %s, %s B, %.1f KB \t\t", name, width, height, file.length(), file.length() / 1024.0));
                    fileInputStream.close();
                    // 默认宽度为 1920
                    int configWidth = Integer.parseInt(PropertiesUtil.getConfig("picture.width"));
                    // 默认高度为 1080
                    int configHeight = Integer.parseInt(PropertiesUtil.getConfig("picture.height"));

                    Scanner scannerInput = new Scanner(System.in);

                    boolean isMove = false;

                    if (width != configWidth && height != configHeight) {
                        isMove = true;
                    } else if (width == configWidth && height != configHeight) {
                        isMove = tip(name, width, height, scannerInput);
//                    } else if (width != configWidth && height == configHeight) {
                    } else if (width != configWidth) {
                        isMove = tip(name, width, height, scannerInput);
                    }
                    if (isMove) {
                        System.out.print(String.format("从 [%s] 移动到: [%s]", file.getPath(), pathB + File.separator + name));
                        file.renameTo(new File(pathB + File.separator + name));
                    }
                    System.out.println();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("结束");

    }

    private static boolean tip(String name, int width, int height, Scanner scannerInput) {
        boolean isMove;
        System.out.println(String.format("图片 [%s] 的宽度为 %s 高度为 [%s] 是否要保留(Y/N)?", name, width, height));
        String str = scannerInput.next();
        while (null == str) {
            System.out.println("输入不能为空，请输入Y(是) 或者 N(否)");
            str = scannerInput.next();
        }
        isMove = "N".equalsIgnoreCase(str) || "n".equalsIgnoreCase(str);
        return isMove;
    }

    /**
     * 4 对比壁纸和a文件夹中图片，将重复的移动到b文件夹
     */
    private static void step4() {
        System.out.println("###########【4、对比壁纸和 a 文件夹中图片，将重复的移动到 b 文件夹】###########");
        System.out.println(String.format("壁纸:[%s]", pathP));
        System.out.println(String.format("a:[%s]", pathA));
        System.out.println(String.format("b:[%s]", pathB));
        System.out.println("开始");

        File filep = new File(pathP);
        File filea = new File(pathA);

        // 先从文件中读取已经 md5 过的图片
        boolean isWriteFile = false;
        Map<String, String> newMap = new HashMap<>();


        Map<String, String> mappNew = null;
        try {
            if (FileUtil.existsFile(mapTxtPath)) {
                String jsonStr = FileUtil.readFromFileByByte(mapTxtPath);
                if (StringUtils.isNotBlank(jsonStr)) {
                    mappNew = (Map<String, String>) JsonMapper.fromJsonString(jsonStr, Map.class);
                } else {
                    System.out.println(String.format("从 [%s] 中读取的内容为空", mapTxtPath));
                }
            } else {
                System.out.println(String.format("在 [%s] 没有找到 map.txt 文件, map 集合为空", mapTxtPath));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 图片文件夹
        if (mappNew == null) {
            mappNew = getDirMD5(filep, true);
            isWriteFile = true;
        }


        // a 文件夹
        Map<String, String> mapa = getDirMD5(filea, true);

        assert mappNew != null;
        String[] keysp = new String[mappNew.values().size()];
        String[] valuesp = new String[mappNew.values().size()];
        assert mapa != null;
        String[] keysa = new String[mapa.values().size()];
        String[] valuesa = new String[mapa.values().size()];

        // 将 map 的 key、value 转换成数组
        int indexp = 0;
        for (Map.Entry<String, String> entry : mappNew.entrySet()) {
            keysp[indexp] = entry.getKey();
            valuesp[indexp] = entry.getValue();
            indexp++;
        }
        int indexa = 0;
        for (Map.Entry<String, String> entry : mapa.entrySet()) {
            keysa[indexa] = entry.getKey();
            valuesa[indexa] = entry.getValue();
            indexa++;
        }

        for (int i = 0; i < valuesp.length; i++) {
            String filepi = keysp[i];
            String vpi = valuesp[i];
            String fileNamepi;
            if (isWriteFile) {
                fileNamepi = filepi.substring(filepi.lastIndexOf("\\") + 1, filepi.lastIndexOf("."));
            } else {
                fileNamepi = filepi;
            }
            if (isWriteFile) {
                newMap.put(fileNamepi, vpi);
            }

            for (int j = 0; j < valuesa.length; j++) {
                String fileaj = keysa[j];
                String vaj = valuesa[j];

                if (vpi.equals(vaj)) {
                    String fileNameaj = fileaj.substring(fileaj.lastIndexOf("\\") + 1, fileaj.lastIndexOf("."));
                    System.out.println("相同的【" + fileNamepi + "、" + fileNameaj + "】，将 " + fileaj + " 移动到 b 文件夹");
                    FileUtil.fileCopy(fileaj, pathB);
                    FileUtil.deleteDir(fileaj);
                }
            }
        }
        if (isWriteFile) {
            String content = JsonMapper.toJsonString(newMap);
            try {
                FileUtil.writeToFileByByte(mapTxtPath, content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("结束");
    }

    /**
     * 5 将a文件夹中的图片移动到壁纸文件夹，同时移动到image文件夹之后删除a文件夹中的图片
     */
    private static void step5() {
        System.out.println("###########【5、将 a 下面的图片移动到壁纸目录同时备份到 image 目录下 】###########");
        System.out.println(String.format("壁纸:[%s]", pathP));
        System.out.println(String.format("a:[%s]", pathA));
        System.out.println(String.format("image:[%s]", pathImage));
        System.out.println("开始");

        File filea = new File(pathA);
        File[] fileArra = filea.listFiles();

        File fileP = new File(pathP);
        int indexp = Objects.requireNonNull(fileP.listFiles()).length;

        assert fileArra != null;
        for (int i = 0; i < fileArra.length; i++) {
            File fa = fileArra[i];
            int length = Integer.parseInt(PropertiesUtil.getConfig("length"));

            String namea = (i + indexp - length) + ".jpg";

            fa.renameTo(new File(pathImage + File.separator + namea));
            System.out.println(String.format("移动 [%s] 到 [%s]", fa + File.separator + namea, pathImage + File.separator + namea));
            FileUtil.fileCopy(pathImage, pathP);
            FileUtil.deleteDir(fa + File.separator + namea);
        }
        System.out.println("移动完成");


        // 将新的图片md5信息写入文件中
        System.out.println("将新增的图片md5信息追加到 map 中");
        Map<String, String> mappNew = null;
        try {
            if (FileUtil.existsFile(mapTxtPath)) {
                String jsonStr = FileUtil.readFromFileByByte(mapTxtPath);
                if (StringUtils.isNotBlank(jsonStr)) {
                    mappNew = (Map<String, String>) JsonMapper.fromJsonString(jsonStr, Map.class);
                } else {
                    System.out.println(String.format("从 [%s] 中读取的内容为空", mapTxtPath));
                }
            } else {
                System.out.println(String.format("在 [%s] 没有找到 map.txt 文件, map 集合为空", mapTxtPath));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 读取image 文件夹下的图片 md5 信息
        File fileImage = new File(pathImage);
        Map<String, String> mapImage = getDirMD5(fileImage, true);

        if (mapImage != null && mapImage.size() > 0) {
            for (String str : mapImage.keySet()) {

                System.out.println(String.format("追加的文件为 [%s]", str));

                String key = str.substring(str.lastIndexOf("\\") + 1, str.lastIndexOf("."));
                mappNew.put(key, mapImage.get(str));
            }
        }


//        if (mapImage != null && mapImage.size() > 0) {
//            String[] keysImage = new String[mapImage.values().size()];
//            String[] valuesImage = new String[mapImage.values().size()];
//
//            int indexImage = 0;
//            for (Map.Entry<String, String> entry : mapImage.entrySet()) {
//                keysImage[indexImage] = entry.getKey();
//                valuesImage[indexImage] = entry.getValue();
//                indexImage++;
//            }
//            for (int i = 0; i < valuesImage.length; i++) {
//                String fileImageI = keysImage[i];
//                String vImageI = valuesImage[i];
//                String fileNameImageI = fileImageI.substring(fileImageI.lastIndexOf("\\") + 1, fileImageI.lastIndexOf("."));
//                assert mappNew != null;
//                mappNew.put(fileNameImageI, vImageI);
//            }
//        }

        // 将map中的key排序
//        newMap = newMap
//                .entrySet()
//                .stream()
//                .sorted(Map.Entry.<String, String>comparingByValue().reversed())
//                .collect(Collectors.toMap(c -> c.getKey(), c -> c.getValue()));

        String content = JsonMapper.toJsonString(mappNew);
        try {
            FileUtil.writeToFileByByte(mapTxtPath, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("追加完成");
        System.out.println("结束");
    }

    /**
     * 6 将壁纸文件中重复的图片移动到a文件夹
     */
    private static void step6() {
        System.out.println("###########【6、将壁纸文件夹中重复的图片挑出来移动到 a 文件夹中】###########");
        System.out.println(String.format("壁纸:[%s]", pathP));
        System.out.println(String.format("a:[%s]", pathA));
        System.out.println("开始");

        File filep = new File(pathP);
        Map<String, String> mapp = getDirMD5(filep, true);

        assert mapp != null;
        String[] keysp = new String[mapp.values().size()];
        String[] valuesp = new String[mapp.values().size()];
        int index = 0;

        for (Map.Entry<String, String> entryp : mapp.entrySet()) {
            keysp[index] = entryp.getKey();
            valuesp[index] = entryp.getValue();
            index++;
            // System.out.println("Key = " + entry.getKey() + ", Value = " +
            // entry.getValue()+", "+index);
        }

        boolean isDeleted = false;

        for (int i = 0; i < valuesp.length; i++) {

            String fileIp = keysp[i];
            String valueIp = valuesp[i];

            for (int j = (i + 1); j < valuesp.length; j++) {
                if ((i + 1) <= valuesp.length) {
                    String fileJp = keysp[j];
                    String vJp = valuesp[j];

                    if (valueIp.equals(vJp)) {
                        // 将相同的图片移动到另外一个目录
                        isDeleted = true;

                        String fileNameIp = fileIp.substring(fileIp.lastIndexOf("\\") + 1, fileIp.lastIndexOf("."));
                        String fileNameJp = fileJp.substring(fileJp.lastIndexOf("\\") + 1, fileJp.lastIndexOf("."));
                        FileUtil.fileCopy(fileIp, pathA);
                        FileUtil.fileCopy(fileJp, pathA);
                        if (Integer.parseInt(fileNameJp) > Integer.parseInt(fileNameIp)) {
                            FileUtil.deleteDir(fileJp);
                            mapp.remove(fileJp);
                        } else {
                            FileUtil.deleteDir(fileIp);
                            mapp.remove(fileIp);
                        }
                        System.out.println("相同的【" + fileNameIp + "、" + fileNameJp + "】, 删除序号大的");
                    }
                }
            }
        }

        Map<String, String> newMap = new HashMap<>();
        // 有删除的就需要重新更新 map 中的 md5 值
        if (isDeleted) {
            for (String str : mapp.keySet()) {
                String key = str.substring(str.lastIndexOf("\\") + 1, str.lastIndexOf("."));
                newMap.put(key, mapp.get(str));
            }

            String content = JsonMapper.toJsonString(newMap);
            try {
                FileUtil.writeToFileByByte(mapTxtPath, content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("结束");
    }

    /**
     * 获取单个文件的MD5值！
     */
    private static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte[] buffer = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 获取文件夹下的所有文件的md5
     *
     * @param file
     * @param listChild
     */
    private static Map<String, String> getDirMD5(File file, boolean listChild) {
        if (!file.isDirectory()) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        String md5;
        File[] files = file.listFiles();

        assert files != null;
        for (File f : files) {
            if (f.isDirectory() && listChild) {
                map.putAll(Objects.requireNonNull(getDirMD5(f, listChild)));
            } else {
                md5 = getFileMD5(f);
                if (md5 != null) {
                    map.put(f.getPath(), md5);
                }
            }
        }
        return map;
    }

}
