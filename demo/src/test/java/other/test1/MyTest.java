package other.test1;


import com.common.mapper.JsonMapper;
import com.common.util.FileUtil;
import com.common.util.StringUtils;
import com.google.common.collect.Maps;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest {

    private static String pathP = "C:\\Users\\zhoubin\\Pictures\\壁纸";
    private static int length = 44;
    private static String filePath = System.getProperty("user.dir") + File.separator + "demo" + File.separator + "src" + File.separator + "test" +
                                     File.separator + "java" + File.separator + "other" + File.separator + "test1" + File.separator + "map.txt";


    // 公司
    private static String pathA = "D:\\Workspaces\\temp\\rename\\a";
    private static String pathB = "D:\\Workspaces\\temp\\rename\\b";
    private static String pathImage = "D:\\Workspaces\\temp\\rename\\image";

    // 家里
//    private static String pathA = "E:\\Workspaces\\temp\\rename\\a";
//    private static String pathB = "E:\\Workspaces\\temp\\rename\\b";
//    private static String pathImage = "E:\\Workspaces\\temp\\rename\\image";


    public static void main(String[] args) throws InterruptedException, IOException {
        // 复制壁纸
        bzAll();
//        bz12();
//        bz46();

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
     * 就执行1、2步
     * @throws InterruptedException
     */
    public static void bz12() throws InterruptedException {
        moveToA(); // 1 将系统 Assets 文件夹下面的图片异动到 a 文件夹

        rename(); // 2 将a文件夹中的图片改名j

    }

    /**
     * 从第四步开始，直接对比 a 文件夹和壁纸文件夹的图片
     * @throws InterruptedException
     */
    public static void bz46() throws InterruptedException {
        repeatFile2(); // 4 对比壁纸和a文件夹中图片，将重复的移动到b文件夹
        System.out.println("\n\n");

        moveToPicutre(); // 5 将a文件夹中的图片移动到壁纸文件夹，同时移动到image文件夹之后删除a文件夹中的图片
        System.out.println("\n\n");

        repeatFile(); // [6] 将壁纸文件夹中重复的图片移动到a文件夹中,并删除壁纸文件夹中名称靠后的图片

    }

    public static void bzAll() throws InterruptedException {
        FileUtil.createDir(pathA);
        FileUtil.createDir(pathB);
        FileUtil.createDir(pathImage);

        moveToA(); // 1 将系统 Assets 文件夹下面的图片异动到 a 文件夹
        System.out.println("\n\n");

        rename(); // 2 将a文件夹中的图片改名j
        System.out.println("\n\n");

        deleteWidthEq1080(); // 3 删除宽度为 1080 的图片
        System.out.println("\n\n");

        repeatFile2(); // 4 对比壁纸和a文件夹中图片，将重复的移动到b文件夹
        System.out.println("\n\n");

        moveToPicutre(); // 5 将a文件夹中的图片移动到壁纸文件夹，同时移动到image文件夹之后删除a文件夹中的图片
        System.out.println("\n\n");

        repeatFile(); // [6] 将壁纸文件夹中重复的图片移动到a文件夹中,并删除壁纸文件夹中名称靠后的图片

    }

    /**
     * 1 将系统文件夹里面的缓存图片复制到a文件夹
     * C:\Users\zhoubin\AppData\Local\Packages\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\LocalState\Assets
     *
     * @throws
     * @Title moveToA (方法名)
     * @require
     * @author zhoubin(作者)
     * @date 2017年5月24日 上午9:32:14
     * @history
     */
    public static void moveToA() {
        String path = "C:\\Users\\zhoubin\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets";

        System.out.println("#########################################【 1 】#########################################");
        System.out.println("开始\t将系统文件夹里面的缓存图片复制到a文件夹");
        System.out.println(String.format("将 %s 文件夹下的图片移动到 %s 文件夹下", path, pathA));
        FileUtil.fileCopy(path, pathA);
        System.out.println("结束\t将系统文件夹里面的缓存图片复制到a文件夹");
    }

    /**
     * 2 将a文件夹中的图片改名
     *
     * @throws @authorzhoubin(作者)
     * @Title rename (方法名)
     * @require
     * @date 2017年4月5日 下午1:22:11
     * @history
     */
    public static void rename() {
        System.out.println("#########################################【 2 】#########################################");
        System.out.println("开始\t将a文件夹中的图片改名");
        System.out.println(String.format("将 %s 文件夹下的图片重命名", pathA));

        // 文件改名字
        File file = new File(pathA);
        File[] fileArr = file.listFiles();

        for (int i = 0; i < fileArr.length; i++) {
            File f = fileArr[i];
            String name = (i + 1) + ".jpg";
            System.out.println(name);

            f.renameTo(new File(pathA + File.separator + name));
        }
        System.out.println("结束\t将a文件夹中的图片改名");
    }

    /**
     * 3 删除宽度为1080的图片
     *
     * @throws
     * @Title deleteWidthEq1080 (方法名)
     * @require
     * @author zhoubin(作者)
     * @date 2017年5月24日 上午9:32:03
     * @history
     */
    public static void deleteWidthEq1080() {
        System.out.println("#########################################【 3 】#########################################");
        System.out.println("开始\t删除高不为1080和宽不为为1920的图片");
        System.out.println(String.format("删除 %s 文件夹下宽度为 1080 的图片", pathA));
        File filea = new File(pathA);
        File[] fileArra = filea.listFiles();

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

                    if (width != 1920 && height != 1080) {
                        System.out.print(String.format("%s, 新路径: %s", name, pathB + File.separator + name));
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
        System.out.println("结束\t删除高不为1080和宽不为为1920的图片");

    }

    /**
     * 4 对比壁纸和a文件夹中图片，将重复的移动到b文件夹
     *
     * @throws @author zhoubin(作者)
     * @Title repeatFile2 (方法名)
     * @require
     * @date 2017年4月2日 下午11:38:14
     * @history
     */
    public static void repeatFile2() {
        System.out.println("#########################################【 4 】#########################################");
        System.out.println("开始\t对比壁纸和a文件夹中图片，将重复的移动到b文件夹");
        System.out.println(String.format("对比壁纸 %s 文件夹和 %s 文件夹中图片，将重复的移动到 %s 文件夹", pathP, pathA, pathB));

        File filep = new File(pathP);
        File filea = new File(pathA);
        Map<String, String> mapp = null;

        // 先从文件中读取已经 md5 过的图片
        boolean isWriteFile = false;
        Map<String, String> newMap = Maps.newHashMap();
        try {
            if (FileUtil.existsFile(filePath)) {
                String jsonStr = FileUtil.readFromFileByByte(filePath);
                if (StringUtils.isNotBlank(jsonStr)) {
                    mapp = (Map<String, String>) JsonMapper.fromJsonString(jsonStr, Map.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 图片文件夹
        if (mapp == null) {
            mapp = getDirMD5(filep, true);
            isWriteFile = true;
        }
        // a 文件夹
        Map<String, String> mapa = getDirMD5(filea, true);

        String[] keysp = new String[mapp.values().size()];
        String[] valuesp = new String[mapp.values().size()];
        String[] keysa = new String[mapa.values().size()];
        String[] valuesa = new String[mapa.values().size()];

        // 将 map 的 key、value 转换成数组
        int indexp = 0;
        for (Map.Entry<String, String> entry : mapp.entrySet()) {
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
                    System.out.println("相同的【" + fileNamepi + "、" + fileNameaj + "】");
                    FileUtil.fileCopy(fileaj, pathB);
                    FileUtil.deleteDir(fileaj);
                }
            }
        }
        if (isWriteFile) {
            String content = JsonMapper.toJsonString(newMap);
            try {
                FileUtil.writeToFileByByte(filePath, content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("结束\t对比壁纸和a文件夹中图片，将重复的移动到b文件夹");
    }

    /**
     * 5 将a文件夹中的图片移动到壁纸文件夹，同时移动到image文件夹之后删除a文件夹中的图片
     *
     * @throws @author zhoubin(作者)
     * @Title moveToPicutre (方法名)
     * @require
     * @date 2017年4月2日 下午11:40:03
     * @history
     */
    public static void moveToPicutre() {
        System.out.println("#########################################【 5 】#########################################");
        System.out.println("开始\t将a文件夹中的图片移动到壁纸文件夹，同时移动到image文件夹之后删除a文件夹中的图片");
        System.out.println(String.format("将 %s 文件夹中的图片移动到壁纸 %s 文件夹，同时移动到 %s 文件夹之后删除 %s 文件夹中的图片", pathA, pathP, pathImage, pathA));

        File filea = new File(pathA);
        File[] fileArra = filea.listFiles();

        File fileP = new File(pathP);
        int indexp = fileP.listFiles().length;

        for (int i = 0; i < fileArra.length; i++) {
            File fa = fileArra[i];
            String namea = (i + indexp - length) + ".jpg";

            fa.renameTo(new File(pathImage + File.separator + namea));
            FileUtil.fileCopy(pathImage, pathP);
            // FileUtil.fileCopy(pathA, pathImage);
            FileUtil.deleteDir(fa + File.separator + namea);
        }



        // 将新的图片md5信息写入文件中
        System.out.println("开始\t将新增的图片md5信息追加到集合中");
        Map<String, String> newMap = null;

        System.out.println(filePath);
        try {
            if (FileUtil.existsFile(filePath)) {
                String jsonStr = FileUtil.readFromFileByByte(filePath);
                newMap = (Map<String, String>) JsonMapper.fromJsonString(jsonStr, Map.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 读取image 文件夹下的图片 md5 信息
        File fileImage = new File(pathImage);
        Map<String, String> mapImage = getDirMD5(fileImage, true);
        if (mapImage != null && mapImage.size() > 0) {
            String[] keysImage = new String[mapImage.values().size()];
            String[] valuesImage = new String[mapImage.values().size()];

            int indexImage = 0;
            for (Map.Entry<String, String> entry : mapImage.entrySet()) {
                keysImage[indexImage] = entry.getKey();
                valuesImage[indexImage] = entry.getValue();
                indexImage++;
            }
            for (int i = 0; i < valuesImage.length; i++) {
                String fileImageI = keysImage[i];
                String vImageI = valuesImage[i];
                String fileNameImageI = fileImageI.substring(fileImageI.lastIndexOf("\\") + 1, fileImageI.lastIndexOf("."));
                newMap.put(fileNameImageI, vImageI);
            }
        }

        // 将map中的key排序
//        newMap = newMap
//                .entrySet()
//                .stream()
//                .sorted(Map.Entry.<String, String>comparingByValue().reversed())
//                .collect(Collectors.toMap(c -> c.getKey(), c -> c.getValue()));

        String content = JsonMapper.toJsonString(newMap);
        try {
            FileUtil.writeToFileByByte(filePath, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束\t将新增的图片md5信息追加到集合中");

        System.out.println("结束\t将a文件夹中的图片移动到壁纸文件夹，同时移动到image文件夹之后删除a文件夹中的图片");
    }

    /**
     * 6 将壁纸文件中重复的图片移动到a文件夹
     *
     * @throws @author zhoubin(作者)
     * @Title repeatFile (方法名)
     * @require
     * @date 2017年3月29日 下午2:57:37
     * @history
     */
    public static void repeatFile() {
        System.out.println("#########################################【 6 】#########################################");
        System.out.println("开始\t将壁纸文件中重复的图片移动到a文件夹");
        System.out.println(String.format("将壁纸 %s 文件夹中重复的图片移动到 %s 文件夹", pathP, pathA));

        File filep = new File(pathP);
        Map<String, String> mapp = getDirMD5(filep, true);

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

        for (int i = 0; i < valuesp.length; i++) {

            String fileIp = keysp[i];
            String valueIp = valuesp[i];

            for (int j = (i + 1); j < valuesp.length; j++) {
                if ((i + 1) <= valuesp.length) {
                    String fileJp = keysp[j];
                    String vJp = valuesp[j];

                    if (valueIp.equals(vJp)) {
                        // 将相同的图片移动到另外一个目录

                        String fileNameIp = fileIp.substring(fileIp.lastIndexOf("\\") + 1, fileIp.lastIndexOf("."));
                        String fileNameJp = fileJp.substring(fileJp.lastIndexOf("\\") + 1, fileJp.lastIndexOf("."));
                        FileUtil.fileCopy(fileIp, pathA);
                        FileUtil.fileCopy(fileJp, pathA);
                        if (Integer.parseInt(fileNameJp) > Integer.parseInt(fileNameIp)) {
                            FileUtil.deleteDir(fileJp);
                        } else {
                            FileUtil.deleteDir(fileIp);
                        }
                        // FileUtil.fileCopy(pathj,
                        // pathB+File.separator+fileNamei.substring(0,
                        // fileNamei.lastIndexOf("."))+"-"+fileNamej);

                        System.out.println("相同的【" + fileNameIp + "、" + fileNameJp + "】");
                        System.out.println(fileJp);
                    }
                }
            }
        }

        System.out.println("结束\t将壁纸文件中重复的图片移动到a文件夹");
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

    /**
     * 获取单个文件的MD5值！
     *
     * @param file
     * @return
     * @throws @author zhoubin(作者)
     * @Title getFileMD5 (方法名)
     * @require
     * @date 2017年3月29日 下午2:57:28
     * @history
     */
    private static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
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
     * @return
     * @throws @author zhoubin(作者)
     * @Title getDirMD5 (方法名)
     * @require
     * @date 2017年3月29日 下午2:57:06
     * @history
     */
    private static Map<String, String> getDirMD5(File file, boolean listChild) {
        if (!file.isDirectory()) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        String md5;
        File files[] = file.listFiles();

        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory() && listChild) {
                map.putAll(getDirMD5(f, listChild));
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
