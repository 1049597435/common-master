package com.tzx;

/**
 * @ProjectName: generator
 * @Package: com.tzx
 * @ClassName: Templet
 * @Description: 模板库
 * @Author: 唐志翔
 * @Date: 2020/4/8 0008 11:45
 * @Version: 1.0
 * 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的
 */
public class Templet {

    public static final String ITEM = "package %s.item;\n" +
            "\n" +
            "import %s.Demo;\n" +
            "\n" +
            "/**\n" +
            " * @ClassName: DemoItem\n" +
            " * @Description: 返回数据扩展类\n" +
            " * @Author: 唐志翔\n" +
            " * @Version: 1.0\n" +
            " * 代码生成器\n" +
            " */\n" +
            "public class DemoItem extends Demo {\n" +
            "}";

    public static final String SEARCH = "package %s.search;\n" +
            "\n" +
            "/**\n" +
            " * @ClassName: DemoSearch\n" +
            " * @Description: 查询Search类\n" +
            " * @Author: 唐志翔\n" +
            " * @Version: 1.0\n" +
            " * 代码生成器\n" +
            " */\n" +
            "public class DemoSearch {\n" +
            "    /** 页码 */\n" +
            "    protected int page;\n" +
            "    /** 条目数 */\n" +
            "    protected int limit;\n" +
            "    /** 开始时间 */\n" +
            "    private String beginDate;\n" +
            "    /** 结束时间*/\n" +
            "    private String endDate;\n" +
            "\n" +
            "    public int getPage() {\n" +
            "        return page;\n" +
            "    }\n" +
            "\n" +
            "    public void setPage(int page) {\n" +
            "        this.page = page;\n" +
            "    }\n" +
            "\n" +
            "    public int getLimit() {\n" +
            "        return limit;\n" +
            "    }\n" +
            "\n" +
            "    public void setLimit(int limit) {\n" +
            "        this.limit = limit;\n" +
            "    }\n" +
            "\n" +
            "    public String getBeginDate() {\n" +
            "        return beginDate;\n" +
            "    }\n" +
            "\n" +
            "    public void setBeginDate(String beginDate) {\n" +
            "        this.beginDate = beginDate;\n" +
            "    }\n" +
            "\n" +
            "    public String getEndDate() {\n" +
            "        return endDate;\n" +
            "    }\n" +
            "\n" +
            "    public void setEndDate(String endDate) {\n" +
            "        this.endDate = endDate;\n" +
            "    }\n" +
            "}";

    public static final String INTEFACE = "package %s;\n" +
            "\n" +
            "import myBean.Demo;\n" +
            "import myBean.item.DemoItem;\n" +
            "import myBean.search.DemoSearch;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "/**\n" +
            " * @ClassName: DemoService\n" +
            " * @Description: 业务接口基类\n" +
            " * @Author: 唐志翔\n" +
            " * @Version: 1.0\n" +
            " * 代码生成器\n" +
            " */\n" +
            "public interface DemoService {\n" +
            "    List<DemoItem> getDemoListBySearch(DemoSearch search);\n" +
            "\n" +
            "    DemoItem getDemoById(Integer id);\n" +
            "\n" +
            "    int insertDemo(Demo demo);\n" +
            "\n" +
            "    int updateDemo(Demo demo);\n" +
            "\n" +
            "    int deleteDemo(Integer id);\n" +
            "}\n";

    public static final String IMPL = "package %s.impl;\n" +
            "\n" +
            "import com.github.pagehelper.PageHelper;\n" +
            "import myDao.DemoMapper;\n" +
            "import myBean.Demo;\n" +
            "import myBean.example.DemoExample;\n" +
            "import myBean.item.DemoItem;\n" +
            "import myBean.search.DemoSearch;\n" +
            "import %s.DemoService;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Service;\n" +
            "import org.springframework.util.StringUtils;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "/**\n" +
            " * @ClassName: DemoServiceImpl\n" +
            " * @Description: 实现类\n" +
            " * @Author: 唐志翔\n" +
            " * @Version: 1.0\n" +
            " * 代码生成器\n" +
            " */\n" +
            "@Service\n" +
            "public class DemoServiceImpl implements DemoService{\n" +
            "\n" +
            "    @Autowired\n" +
            "    private DemoMapper demoMapper;\n" +
            "\n" +
            "    @Override\n" +
            "    public List<DemoItem> getDemoListBySearch(DemoSearch search) {\n" +
            "        if(search == null){\n" +
            "            return demoMapper.selectByExample(null);\n" +
            "        }\n" +
            "        if(search.getPage() > 0 && search.getLimit() > 0){\n" +
            "            PageHelper.startPage(search.getPage(),search.getLimit());\n" +
            "        }\n" +
            "        DemoExample example = new DemoExample();\n" +
            "        DemoExample.Criteria criteria = example.createCriteria();\n" +
            "\n" +
            "        return demoMapper.selectByExample(example);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public DemoItem getDemoById(Integer id) {\n" +
            "        return demoMapper.selectByPrimaryKey(id);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public int insertDemo(Demo demo) {\n" +
            "        return demoMapper.insertSelective(demo);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public int updateDemo(Demo demo) {\n" +
            "        return demoMapper.updateByPrimaryKeySelective(demo);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public int deleteDemo(Integer id) {\n" +
            "        return demoMapper.deleteByPrimaryKey(id);\n" +
            "    }\n" +
            "}\n";

    public static final String CONTROLLER = "package %s;\n" +
            "\n" +
            "import com.github.pagehelper.PageInfo;\n" +
            "import myCommons.ResultBean;\n" +
            "import myCommons.ResultUtil;\n" +
            "import myBean.Demo;\n" +
            "import myBean.item.DemoItem;\n" +
            "import myBean.search.DemoSearch;\n" +
            "import myService.DemoService;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Controller;\n" +
            "import org.springframework.ui.Model;\n" +
            "import org.springframework.web.bind.annotation.PostMapping;\n" +
            "import org.springframework.web.bind.annotation.RequestMapping;\n" +
            "import org.springframework.web.bind.annotation.ResponseBody;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "/**\n" +
            " * @ClassName: DemoController\n" +
            " * @Description: 页面交互控制器\n" +
            " * @Author: 唐志翔\n" +
            " * @Version: 1.0\n" +
            " * 代码生成\n" +
            " */\n" +
            "@Controller\n" +
            "@RequestMapping(\"demo\")\n" +
            "public class DemoController {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private DemoService demoService;\n" +
            "\n" +
            "    @RequestMapping(\"listView\")\n" +
            "    public String listView(){\n" +
            "        return \"demo/list\";\n" +
            "    }\n" +
            "\n" +
            "    @RequestMapping(\"addView\")\n" +
            "    public String addView(){\n" +
            "        return \"demo/add\";\n" +
            "    }\n" +
            "\n" +
            "    @RequestMapping(\"editView\")\n" +
            "    public String editView(Integer id, Model model) {\n" +
            "        model.addAttribute(\"demo\",demoService.getDemoById(id));\n" +
            "        return \"demo/edit\";\n" +
            "    }\n" +
            "\n" +
            "    @PostMapping(\"getData\")\n" +
            "    @ResponseBody\n" +
            "    public ResultUtil getData(DemoSearch search){\n" +
            "        List<DemoItem> list = demoService.getDemoListBySearch(search);\n" +
            "        PageInfo<DemoItem> info = new PageInfo<>(list);\n" +
            "        return new ResultUtil(0,info.getTotal(),info.getList());\n" +
            "    }\n" +
            "\n" +
            "    @PostMapping(\"toInsert\")\n" +
            "    @ResponseBody\n" +
            "    public ResultBean toInsert(Demo demo){\n" +
            "        demo.setCustomerState(1);\n" +
            "        if(demoService.insertDemo(demo) > 0){\n" +
            "            return new ResultBean(true);\n" +
            "        }\n" +
            "        return new ResultBean(false);\n" +
            "    }\n" +
            "\n" +
            "    @PostMapping(\"toUpdate\")\n" +
            "    @ResponseBody\n" +
            "    public ResultBean toUpdate(Demo demo){\n" +
            "        if(demoService.updateDemo(demo) > 0){\n" +
            "            return new ResultBean(true);\n" +
            "        }\n" +
            "        return new ResultBean(false);\n" +
            "    }\n" +
            "\n" +
            "    @PostMapping(\"toDelete\")\n" +
            "    @ResponseBody\n" +
            "    public ResultBean toDelete(Integer id){\n" +
            "        if(demoService.deleteDemo(id) > 0){\n" +
            "            return new ResultBean(true);\n" +
            "        }\n" +
            "        return new ResultBean(false);\n" +
            "    }\n" +
            "}\n";
}
