<%--
  Created by IntelliJ IDEA.
  User: 22945
  Date: 2020-8-11
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>

<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>

    <script src="ECharts/echarts.min.js"></script>
    <script src="jquery/jquery-1.11.1-min.js"></script>

    <script>
        $(function () {
            getCharts();
        })


        function getCharts() {


            $.ajax({
                url:"workbench/transaction/getCharts.do",
                type:"get",
                dataType:"json",
                success:function (data) {
                    var myChart=echarts.init(document.getElementById("main"));
                    option = {
                        title: {
                            text: '交易阶段漏斗图',
                            subtext: '各阶段交易数量的漏斗图'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c}/"+data.total
                        },
                        toolbox: {
                            feature: {
                                dataView: {readOnly: false},
                                restore: {},
                                saveAsImage: {}
                            }
                        },


                        series: [
                            {
                                name:'交易阶段',
                                type:'funnel',
                                left: '10%',
                                top: 60,
                                //x2: 80,
                                bottom: 60,
                                width: '80%',
                                // height: {totalHeight} - y - y2,
                                min: 0,
                                max: data.total,
                                minSize: '0%',
                                maxSize: '100%',
                                sort: 'descending',
                                gap: 2,
                                label: {
                                    show: true,
                                    position: 'inside'
                                },
                                labelLine: {
                                    length: 10,
                                    lineStyle: {
                                        width: 1,
                                        type: 'solid'
                                    }
                                },
                                itemStyle: {
                                    borderColor: '#fff',
                                    borderWidth: 1
                                },
                                emphasis: {
                                    label: {
                                        fontSize: 20
                                    }
                                },
                                data: data.dataList

                                    /*[
                                        {value: 60, name: '访问'},
                                        {value: 40, name: '咨询'},
                                        {value: 20, name: '订单'},
                                        {value: 80, name: '点击'},
                                        {value: 100, name: '展现'}
                                      ]

                                     */
                            }
                        ]
                    };
                    myChart.setOption(option);
                }
            })
            // alert("123");

            // 指定图表的配置项和数据

            // 使用刚指定的配置项和数据显示图表。



        }


    </script>
</head>
<body>
<div id="main" style="width: 600px;height:400px;"></div>
123
</body>
</html>
