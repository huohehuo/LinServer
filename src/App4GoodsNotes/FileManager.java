package App4GoodsNotes;


import Utils.Lg;

import javax.naming.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileManager {

    private static FileManager mInstance;

    public static FileManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (FileManager.class) {
                if (mInstance == null) {
                    mInstance = new FileManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 通过图片文件夹的路径获取该目录下的图片
     */
    public static List<String> getImgListByDir(String dir) {
        ArrayList<String> imgPaths = new ArrayList<>();
        File directory = new File(dir);
        if (directory == null || !directory.exists()) {
            Lg.e("directory为空");
            return imgPaths;
        }
        File[] files = directory.listFiles();
        //对文件进行排序
        Lg.e("files",files);
        if (null==files || files.length<= 0)return imgPaths;
        Arrays.sort(files, new FileComparator());
        for (File file : files) {
            String path = file.getAbsolutePath();
            if (FileManager.isPicFile(path)) {
//                if (path.contains("camera")){
                    imgPaths.add(path);
//                }
            }
        }
        return imgPaths;
    }
    //查找出指定地址下的excel文件
    public static List<String> getXlsListByDir(String dir) {
        ArrayList<String> imgPaths = new ArrayList<>();
        File directory = new File(dir);
        if (directory == null || !directory.exists()) {
            Lg.e("directory为空");
            return imgPaths;
        }
        File[] files = directory.listFiles();
        //对文件进行排序
//        Lg.e("files",files);
        if (null==files || files.length<= 0)return imgPaths;
        Arrays.sort(files, new FileComparator());
        for (File file : files) {
            String path = file.getAbsolutePath();
            if (FileManager.isXlsxFile(path)) {
//                if (path.contains("camera")){
                imgPaths.add(path);
//                }
            }
        }
        return imgPaths;
    }

    //截取文件夹中的图片数据
    private ArrayList<String> dealXls(List<String> list){
        ArrayList<String> container = new ArrayList<>();
        if (null==list || list.size()<=0){
            return container;
        }
        try {
            for (int i = 0; i < list.size(); i++) {
                String[] split = list.get(i).split("\\\\");
                container.add(split[split.length-1]);
            }
        }catch (Exception e){
            return container;
        }
        return container;
    }

    /**
     * 是否是图片文件
     */
    public static boolean isPicFile(String path) {
        path = path.toLowerCase();
        if (path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png")) {
            return true;
        }
        return false;
    }
    public static boolean isXlsxFile(String path) {
        path = path.toLowerCase();
        if (path.endsWith(".xlsx") || path.endsWith(".xls")) {
            return true;
        }
        return false;
    }
    public static void deletePic(String dir){
        File f = new File(dir);
        if (f.exists()) {
            Lg.e("存在文件，删除",dir);
            f.delete();
        }else{
            Lg.e("不存在文件",dir);
        }
    }



    static class FileComparator implements Comparator<File> {

        @Override
        public int compare(File lhs, File rhs) {
            if (lhs.lastModified() < rhs.lastModified()) {
                return 1;
            } else {
                return -1;
            }
        }
    }



}
