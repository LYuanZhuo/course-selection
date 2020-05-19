package com.lyuan.admin;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyuan.admin.classroom.entity.Classroom;
import com.lyuan.admin.classroom.service.IClassroomService;
import com.lyuan.admin.comment.entity.Comment;
import com.lyuan.admin.comment.service.ICommentService;
import com.lyuan.admin.teacher.entity.Teacher;
import com.lyuan.admin.teacher.service.ITeacherService;
import com.lyuan.admin.user.entity.User;
import com.lyuan.admin.user.service.IUserService;
import com.lyuan.applet.basecourse.mapper.AppletBasecourseMapper;
import com.lyuan.common.annotation.Interception;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2020-03-24
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    ITeacherService iTeacherService;
    @Autowired
    IClassroomService iClassroomService;
    @Autowired
    IUserService iUserService;
    @Autowired
    ICommentService iCommentService;
    @Resource
    AppletBasecourseMapper appletBasecourseMapper;
    @GetMapping
    @Interception
    public BaseResponse get(){
         return null;
    }
}

/**
 * 随机生成中文姓名，性别，Email，手机号，住址
 * @author X-rapido
 */
class RandomValue {
    private static String[] homework ={"作业巨多啊","作业有点多","作业不算多就是有点烦","作业很少","一般般不算很多","基本没有作业","主要是课堂作业，课后很少",
            "上课会做ppt上的作业","隔周一次作业","作业又多又麻烦"};
    private static String[] callname ={"每次都点啊，还是全点","快下课的时候会点","每节课都点名","没有一节不点的","经常点，还经常点我","有时点","看老师心情","隔周点",
            "不点名，要签到"
            ,"让学委负责查人数","基本不点","很少点，点也是抽点"};
    private static String[]  comment={"五星好评，能学到很多东西","很赞的课，选了不亏","老师很nice，认真听的话能学到很多","只要每次按时上课交作业这个课能学很多东西",
            "感觉挺不错的，很有趣,老师上课会问一些很好玩的问题一起讨论，人也很nice","对我来说一般般吧，但也不算讨厌","emmm感觉一般，不是很喜欢，作业也很烦"
            ,"不谈了，这个课是我的噩梦","这老师巨凶，想开小差的别选","这老师超级好玩！"};
    private static int[] grades={2016,2017,2018,2019};
    private static String[] notes={"多媒体教室","普通教室"};
    private static String[] avatars = {"https://img2.woyaogexing.com/2020/04/02/8e6b825c3dd64cb3bd019d35c717ab1a!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/b5a970620d56415b82f4d41853cf5588!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/1eeacff59ee74ced88a53274629ed5bc!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/5819da777a5d4953a3def7a37e144b78!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/116ca8e660bb4e80a75a24279cfe2466!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/e2c88738af0741b29864e000622e8969!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/09866bc7e3c14b5eaf34f2a71025da78!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/f9462e6c546a4d3390d3734ab4ba5af3!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/4215a6d4bda4430f938bc97c4665be8a!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/8472137ec3c84382bdca78ab7a03c7f2!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/c7d669405a35435d837579e5fc0a165f!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/07a17cffe1f94bf1896934c7405b6ced!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/8ef8e7c2580640db81f6575d5e8eb82b!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/8ef8e7c2580640db81f6575d5e8eb82b!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/7a2bd4a62a6540e195fab8b5eeb9309e!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/74f3fc10310b48d484c08aa4c16d2d46!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/02/5f6f0bea70624c7fbce5fabb5c34fd9f!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/34df299d1cd54937afd239ab7418a222!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/36cb976a972841f895b7b7b0c870ebc8!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/44d3fa71c6534f1087cec9fbde053656!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/37fbc0443ec44712ac47a6084938889d!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/cc1f60eb108249838cfb1b8644c3078b!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/3d219f63edcd4a6284c51e30a1110ed7!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/4072a389d896480c926a346183339efe!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/5445f1f16d1642788962fbd14c405513!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/afb4c5eade88433dac6a8fbd5c92e3e1!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/cdf28b5d117e4129bab808e2a2480c8b!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/7ade398a025b48ec870fa06f6e81a9a6!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/72439b1ddc2e40099f592f5256120e90!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/e064c15ffe8d49d4a5348ea331dccdfe!400x400.jpeg", "https://img2.woyaogexing.com/2020/04/01/4aed11f49e6745349b67778c726b4382!400x400.jpeg", "https://img2.woyaogexing.com/2020/03/31/329852d932a14e7481819f77586488e1!400x400.jpeg", "https://img2.woyaogexing.com/2020/03/31/61334d58358f4550ac21ae97b344de85!400x400.jpeg", "https://img2.woyaogexing.com/2020/03/31/3cb304c4a2894b32bbd275390f884aa7!400x400.jpeg", "https://img2.woyaogexing.com/2020/03/31/ac6c261ba93f4028ac157d1784035613!400x400.jpeg"};
    public static String[] pic = {"http://127.0.0.1:8081/static/image/87ddf682-495b-4f11-8b84-7cac1e4c10d3.jpg","http://127.0.0.1:8081/static/image/3cd238c8-e0b6-43e4-9c07-5e11513f63a6.jpg","http://127.0.0.1:8081/static/image/1c349a71-eb57-4d7e-a01c-35d9464a2e9e.jpg","http://127.0.0.1:8081/static/image/6597491c-1ee7-4139-8a1d-bb84d75009ec.jpeg"};
    private static String[] colleges = {"数学与信息学院（软件学院）", "工程学院", "水利与土木工程学院", "艺术学院", "经济管理学院", "电子工程学院", "农学院", "食品学院", "海洋学院", "外国语学院", "园艺学院", "林学与风景园林学院", "兽医学院", "生命科学学院", "资源环境学院", "材料与能源学院", "公共管理学院", "马克思主义学院", "继续教育学院", "人文与法学学院", "动物科学学院"};
    private static String[] buidings={"教一","教二","教三","教四","教五","教六"};
    private static String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static String firstName="赵钱孙李周吴郑王冯陈沈韩杨黄容冼范麦谢梁";
    private static String girl="秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
    private static String boy="伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
    private static String[] road="重庆大厦,黑龙江路,十梅庵街,遵义路,湘潭街,瑞金广场,仙山街,仙山东路,仙山西大厦,白沙河路,赵红广场,机场路,民航街,长城南路,流亭立交桥,虹桥广场,长城大厦,礼阳路,风岗街,中川路,白塔广场,兴阳路,文阳街,绣城路,河城大厦,锦城广场,崇阳街,华城路,康城街,正阳路,和阳广场,中城路,江城大厦,顺城路,安城街,山城广场,春城街,国城路,泰城街,德阳路,明阳大厦,春阳路,艳阳街,秋阳路,硕阳街,青威高速,瑞阳街,丰海路,双元大厦,惜福镇街道,夏庄街道,古庙工业园,中山街,太平路,广西街,潍县广场,博山大厦,湖南路,济宁街,芝罘路,易州广场,荷泽四路,荷泽二街,荷泽一路,荷泽三大厦,观海二广场,广西支街,观海一路,济宁支街,莒县路,平度广场,明水路,蒙阴大厦,青岛路,湖北街,江宁广场,郯城街,天津路,保定街,安徽路,河北大厦,黄岛路,北京街,莘县路,济南街,宁阳广场,日照街,德县路,新泰大厦,荷泽路,山西广场,沂水路,肥城街,兰山路,四方街,平原广场,泗水大厦,浙江路,曲阜街,寿康路,河南广场,泰安路,大沽街,红山峡支路,西陵峡一大厦,台西纬一广场,台西纬四街,台西纬二路,西陵峡二街,西陵峡三路,台西纬三广场,台西纬五路,明月峡大厦,青铜峡路,台西二街,观音峡广场,瞿塘峡街,团岛二路,团岛一街,台西三路,台西一大厦,郓城南路,团岛三街,刘家峡路,西藏二街,西藏一广场,台西四街,三门峡路,城武支大厦,红山峡路,郓城北广场,龙羊峡路,西陵峡街,台西五路,团岛四街,石村广场,巫峡大厦,四川路,寿张街,嘉祥路,南村广场,范县路,西康街,云南路,巨野大厦,西江广场,鱼台街,单县路,定陶街,滕县路,钜野广场,观城路,汶上大厦,朝城路,滋阳街,邹县广场,濮县街,磁山路,汶水街,西藏路,城武大厦,团岛路,南阳街,广州路,东平街,枣庄广场,贵州街,费县路,南海大厦,登州路,文登广场,信号山支路,延安一街,信号山路,兴安支街,福山支广场,红岛支大厦,莱芜二路,吴县一街,金口三路,金口一广场,伏龙山路,鱼山支街,观象二路,吴县二大厦,莱芜一广场,金口二街,海阳路,龙口街,恒山路,鱼山广场,掖县路,福山大厦,红岛路,常州街,大学广场,龙华街,齐河路,莱阳街,黄县路,张店大厦,祚山路,苏州街,华山路,伏龙街,江苏广场,龙江街,王村路,琴屿大厦,齐东路,京山广场,龙山路,牟平街,延安三路,延吉街,南京广场,东海东大厦,银川西路,海口街,山东路,绍兴广场,芝泉路,东海中街,宁夏路,香港西大厦,隆德广场,扬州街,郧阳路,太平角一街,宁国二支路,太平角二广场,天台东一路,太平角三大厦,漳州路一路,漳州街二街,宁国一支广场,太平角六街,太平角四路,天台东二街,太平角五路,宁国三大厦,澳门三路,江西支街,澳门二路,宁国四街,大尧一广场,咸阳支街,洪泽湖路,吴兴二大厦,澄海三路,天台一广场,新湛二路,三明北街,新湛支路,湛山五街,泰州三广场,湛山四大厦,闽江三路,澳门四街,南海支路,吴兴三广场,三明南路,湛山二街,二轻新村镇,江南大厦,吴兴一广场,珠海二街,嘉峪关路,高邮湖街,湛山三路,澳门六广场,泰州二路,东海一大厦,天台二路,微山湖街,洞庭湖广场,珠海支街,福州南路,澄海二街,泰州四路,香港中大厦,澳门五路,新湛三街,澳门一路,正阳关街,宁武关广场,闽江四街,新湛一路,宁国一大厦,王家麦岛,澳门七广场,泰州一路,泰州六街,大尧二路,青大一街,闽江二广场,闽江一大厦,屏东支路,湛山一街,东海西路,徐家麦岛函谷关广场,大尧三路,晓望支街,秀湛二路,逍遥三大厦,澳门九广场,泰州五街,澄海一路,澳门八街,福州北路,珠海一广场,宁国二路,临淮关大厦,燕儿岛路,紫荆关街,武胜关广场,逍遥一街,秀湛四路,居庸关街,山海关路,鄱阳湖大厦,新湛路,漳州街,仙游路,花莲街,乐清广场,巢湖街,台南路,吴兴大厦,新田路,福清广场,澄海路,莆田街,海游路,镇江街,石岛广场,宜兴大厦,三明路,仰口街,沛县路,漳浦广场,大麦岛,台湾街,天台路,金湖大厦,高雄广场,海江街,岳阳路,善化街,荣成路,澳门广场,武昌路,闽江大厦,台北路,龙岩街,咸阳广场,宁德街,龙泉路,丽水街,海川路,彰化大厦,金田路,泰州街,太湖路,江西街,泰兴广场,青大街,金门路,南通大厦,旌德路,汇泉广场,宁国路,泉州街,如东路,奉化街,鹊山广场,莲岛大厦,华严路,嘉义街,古田路,南平广场,秀湛路,长汀街,湛山路,徐州大厦,丰县广场,汕头街,新竹路,黄海街,安庆路,基隆广场,韶关路,云霄大厦,新安路,仙居街,屏东广场,晓望街,海门路,珠海街,上杭路,永嘉大厦,漳平路,盐城街,新浦路,新昌街,高田广场,市场三街,金乡东路,市场二大厦,上海支路,李村支广场,惠民南路,市场纬街,长安南路,陵县支街,冠县支广场,小港一大厦,市场一路,小港二街,清平路,广东广场,新疆路,博平街,港通路,小港沿,福建广场,高唐街,茌平路,港青街,高密路,阳谷广场,平阴路,夏津大厦,邱县路,渤海街,恩县广场,旅顺街,堂邑路,李村街,即墨路,港华大厦,港环路,馆陶街,普集路,朝阳街,甘肃广场,港夏街,港联路,陵县大厦,上海路,宝山广场,武定路,长清街,长安路,惠民街,武城广场,聊城大厦,海泊路,沧口街,宁波路,胶州广场,莱州路,招远街,冠县路,六码头,金乡广场,禹城街,临清路,东阿街,吴淞路,大港沿,辽宁路,棣纬二大厦,大港纬一路,贮水山支街,无棣纬一广场,大港纬三街,大港纬五路,大港纬四街,大港纬二路,无棣二大厦,吉林支路,大港四街,普集支路,无棣三街,黄台支广场,大港三街,无棣一路,贮水山大厦,泰山支路,大港一广场,无棣四路,大连支街,大港二路,锦州支街,德平广场,高苑大厦,长山路,乐陵街,临邑路,嫩江广场,合江路,大连街,博兴路,蒲台大厦,黄台广场,城阳街,临淄路,安邱街,临朐路,青城广场,商河路,热河大厦,济阳路,承德街,淄川广场,辽北街,阳信路,益都街,松江路,流亭大厦,吉林路,恒台街,包头路,无棣街,铁山广场,锦州街,桓台路,兴安大厦,邹平路,胶东广场,章丘路,丹东街,华阳路,青海街,泰山广场,周村大厦,四平路,台东西七街,台东东二路,台东东七广场,台东西二路,东五街,云门二路,芙蓉山村,延安二广场,云门一街,台东四路,台东一街,台东二路,杭州支广场,内蒙古路,台东七大厦,台东六路,广饶支街,台东八广场,台东三街,四平支路,郭口东街,青海支路,沈阳支大厦,菜市二路,菜市一街,北仲三路,瑞云街,滨县广场,庆祥街,万寿路,大成大厦,芙蓉路,历城广场,大名路,昌平街,平定路,长兴街,浦口广场,诸城大厦,和兴路,德盛街,宁海路,威海广场,东山路,清和街,姜沟路,雒口大厦,松山广场,长春街,昆明路,顺兴街,利津路,阳明广场,人和路,郭口大厦,营口路,昌邑街,孟庄广场,丰盛街,埕口路,丹阳街,汉口路,洮南大厦,桑梓路,沾化街,山口路,沈阳街,南口广场,振兴街,通化路,福寺大厦,峄县路,寿光广场,曹县路,昌乐街,道口路,南九水街,台湛广场,东光大厦,驼峰路,太平山,标山路,云溪广场,太清路".split(",");
    private static final String[] email_suffix="@qq.com,@163.com,@163.net,@126.com,@sina.com,@sohu.com".split(",");

    public static String getHomework(){
        return homework[(int)(Math.random()*homework.length)];
    }
    public static String getComment(){
        return comment[(int)(Math.random()*comment.length)];
    }
    public static String getCallname(){
        return callname[(int)(Math.random()*callname.length)];
    }
    public static String getAvatar(){
        return avatars[(int)(Math.random()*avatars.length)];
    }
    public static int getGrades(){
        return grades[(int)(Math.random()*grades.length)];
    }
    public static String getNotes(){
        return notes[(int)(Math.random()*notes.length)];
    }
    public static String getBuilding(){
        return buidings[(int)(Math.random()*buidings.length)];
    }
    public static String getImageUrl(){
        return pic[(int)(Math.random()*pic.length)];
    }
    public static String getCollege() {
        return colleges[(int)(Math.random()*colleges.length)];
    }
    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

    /**
     * 返回Email
     * @param lMin 最小长度
     * @param lMax 最大长度
     * @return
     */
    public static String getEmail(int lMin,int lMax) {
        int length=getNum(lMin,lMax);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int)(Math.random()*base.length());
            sb.append(base.charAt(number));
        }
        sb.append(email_suffix[(int)(Math.random()*email_suffix.length)]);
        return sb.toString();
    }

    /**
     * 返回手机号码
     */
    public static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    public static String getTel() {
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }

    /**
     * 返回中文姓名
     */
    public static String name_sex = "";
    public static String getChineseName() {
        int index=getNum(0, firstName.length()-1);
        String first=firstName.substring(index, index+1);
        int sex=getNum(0,1);
        String str=boy;
        int length=boy.length();
        if(sex==0){
            str=girl;
            length=girl.length();
            name_sex = "女";
        }else {
            name_sex="男";
        }
        index=getNum(0,length-1);
        String second=str.substring(index, index+1);
        int hasThird=getNum(0,5);
        String third="";
        if(hasThird!=1){
            index=getNum(0,length-1);
            third=str.substring(index, index+1);
        }
        return first+second+third;
    }

    /**
     * 返回地址
     * @return
     */
    public static String getRoad() {
        int index=getNum(0,road.length-1);
        String first=road[index];
        String second=String.valueOf(getNum(11,150))+"号";
        String third="-"+getNum(1,20)+"-"+getNum(1,10);
        return first+second+third;
    }

    /**
     * 数据封装
     * @return
     */
    public static Map getAddress() {
        Map map=new HashMap();
        map.put("name", getChineseName());
        map.put("sex", name_sex);
        map.put("road", getRoad());
        map.put("tel", getTel());
        map.put("email", getEmail(6,9));
        return map;
    }

//	public static void main(String[] args) {
//		for (int i = 0; i < 100; i++) {
//			System.out.println(getAddress());
////			System.out.println(getEmailName(6,9));
//		}
//	}
}
