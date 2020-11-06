package cn.com.cgh.util.bit.per;

/**
 * 可以进行当做为属性以及权限来进行做为控制
 */
public class Permission {
    private static final int ALL_SELECT = 1<<1;
    private static final int ALL_INSERT = 1<<2;
    private static final int ALL_UPDATE = 1<<3;
    private static final int ALL_DELETE = 1<<4;

    /**
     *
     */
    private int flag;

    public void setPermission(int permission){
        this.flag = permission;
    }

    public void addPermission(int permission){
        flag = flag|permission;
    }

    public void disable(int permission){
        flag = flag&~permission;
    }

    public boolean isAllow(int permission){
        return (flag&permission)==permission;
    }

    public boolean isNotAllow(int permission){
        return (flag&permission)==0;
    }

    public static void main(String[] args) {
//        Permission permission = new Permission();
//        permission.addPermission(Permission.ALL_DELETE);
//        permission.addPermission(Permission.ALL_DELETE);
//        permission.addPermission(Permission.ALL_INSERT);
//        permission.disable(Permission.ALL_INSERT);
//        System.out.println(permission.flag);

        System.out.println(10&2);
        System.out.println(ALL_SELECT);
        System.out.println(ALL_INSERT);
        System.out.println(ALL_UPDATE);
        System.out.println(ALL_DELETE);

    }
}
