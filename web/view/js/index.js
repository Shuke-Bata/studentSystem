window.onload = function () {
    // 开始存储从数据库获取的学生信息10条
    getInformation(start, count);

    // 增加
    var add = document.getElementById("add");
    add.addEventListener("click", function () {
        // 将保存类型设置为add
        saveType = "add";
        showDealMenu("增加学生信息", 0);
    });

    // 删除
    var del = document.getElementById("delete");
    del.addEventListener("click", function () {
        let checkBoxes = $(".myCheck");
        let len = checkBoxes.length;

        if (confirm("确定删除？")) {
            // 存放所有需要删除的id
            let ids = [];
            for (let i = len - 1; i >= 0; i--) {
                if (checkBoxes[i].checked) {
                    ids.push(students[i].id);
                }
            }
            if (ids.length > 0) {
                // 当所有的全部选出后再进行删除
                deleteInformation(ids);
            } else {
                alert("未选择删除的内容！");
            }

        } else {
            // 取消删除选中的全部置空
            chooseAll = document.getElementById("choose-all");
            if (chooseAll.checked) {
                chooseAll.checked = false;
            }
            for (let i = len - 1; i >= 0; i--) {
                if (checkBoxes[i].checked) {
                    checkBoxes[i].checked = false;
                }
            }
        }
    });

    //修改当鼠标在表格中点击后才有可能触发修改按钮
    studentTable.addEventListener("mousedown", function () {
        let changeButtons = document.getElementsByClassName("change");
        let len = changeButtons.length;
        for (let i = 0; i < len; i++) {
            changeButtons[i].addEventListener("click", function () {
                // 将保存类型设置为change
                saveType = "change";
                showDealMenu("修改学生信息", i);
            });
        }
    });

    // 查询
    var search = document.getElementById("search");
    search.addEventListener("click", function () {
        showSearchMen();
    });

    // 全选和全不选
    var chooseAll = document.getElementById("choose-all");
    chooseAll.addEventListener("click", function () {
        let flag = chooseAll.checked;
        let checkBoxes = $(".myCheck");
        for (let i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].checked = flag;
        }
    });

    // 上一页
    previous = document.getElementById("previous");
    previous.onclick = previousPage;

    // 下一页
    next = document.getElementById("next");
    next.onclick = nextPage;

    /**
     * 展示增、改学生信息的界面
     * @param message 标题
     */
    function showDealMenu(message, num) {

        if (saveType === "change") {
            let i = num;
            //使用jquery所有输入框赋值
            $("#number").val(students[i].number);
            $("#name").val(students[i].name);
            $("#college").val(students[i].college);
            $("#major").val(students[i].major);
            $("#grade").val(students[i].grade);
            $("#class").val(students[i].stuClass);
            $("#age").val(students[i].age);
        }

        // 更换标题
        document.getElementById("deal-information-title").innerHTML = message;
        // 展示界面
        dealInformation.style.visibility = "visible";
        // 添加遮罩效果
        document.getElementById("overlay").style.visibility = "visible";

        // 提交按钮事件
        let save = document.getElementById("save");
        save.onclick = function () {
            saveInformation(saveType, num);
        };

        // 取消保存
        let cancel = document.getElementById("cancel");
        cancel.onclick = function () {
            hiddenDealMenu();
        };
    }

    /**
     * 隐藏增、改学生信息的界面
     */
    function hiddenDealMenu() {
        //将保存类型赋空
        saveType = null;

        //隐藏界面
        dealInformation.style.visibility = "";
        //取消遮罩效果
        document.getElementById("overlay").style.visibility = "";

        //使用jquery清空所有输入框
        $(".deal-input").val("");
    }

    /**
     * 保存增加或者修改的信息，然后封装成json发送给后台
     */
    function saveInformation(saveType, num) {

        let number = $("#number").val();
        let name = $("#name").val();
        let college = $("#college").val();
        let major = $("#major").val();
        let grade = $("#grade").val();
        let stuClass = $("#class").val();
        let age = $("#age").val();

        let student = null;
        if (saveType === "add") {
            //以json形式存储
            student = {
                number: number,
                name: name,
                college: college,
                major: major,
                grade: grade,
                stuClass: stuClass,
                age: age,
                saveType: saveType
            };
        } else {
            if (number === students[num]["number"]) {
                number = null;
            }
            //以json形式存储
            student = {
                id: students[num]["id"],
                number: number,
                name: name,
                college: college,
                major: major,
                grade: grade,
                stuClass: stuClass,
                age: age,
                saveType: saveType
            };
        }


        //指定后台接口地址
        // let url = "http://localhost:8080/SaveStudent";
        let url = "http://121.36.58.0:8080/student/SaveStudent";

        //发送请求
        Ajax(url, "post", student, function (res) {
            console.log("请求成功", res);
            //获取后台传回的数据，
            if (res.success) {
                hiddenDealMenu();
                if (saveType === "add") {
                    sumStudents += 1;

                    start = sumStudents - (sumStudents % count);
                    getInformation(start, count);
                } else {
                    let i = num;
                    if (student.number != null) {
                        students[i].number = student.number;
                    }
                    students[i].name = student.name;
                    students[i].college = student.college;
                    students[i].major = student.major;
                    students[i].grade = student.grade;
                    students[i].stuClass = student.stuClass;
                    students[i].age = student.age;

                    //插入数据
                    insertToTable(students);
                }
            }
            alert(res.msg);

        }, function (err) {
            console.log("请求失败", err);
        })
    }

    /**
     * 删除学生信息，把学号发给后台
     */
    function deleteInformation(ids) {
        //以json形式存储
        let datas = {
            ids: ids
        };

        //指定后台接口地址
        //let url = "http://localhost:8080/DeleteStudents";
        let url = "http://121.36.58.0:8080/student/DeleteStudents";

        //发送请求
        Ajax(url, "post", datas, function (res) {
            // 获取后台传回的数据
            // 当删除成功后才进行清空和再次请求
            if (res.success) {
                chooseAll = document.getElementById("choose-all");
                if (chooseAll.checked) {
                    chooseAll.checked = false;
                }

                sumStudents -= ids.length;

                if (tableType === "searchTable") {
                    for (let i = 0; i < ids.length; i++) {
                        for (let k = 0; k < students.length; k++) {
                            console.log(ids[i], students[k].id);
                            if (students[k].id === ids[i]) {
                                students.splice(k, 1);
                            }
                        }
                    }
                    //重新插入
                    insertToTable(students);
                } else {
                    // 当前页面信息全部删除后跳页
                    if (start === sumStudents && sumStudents !== 0) {
                        start -= count;
                    }
                    //重新获取并插入
                    getInformation(start, count);
                }

            }
            alert(res.msg);

        }, function (err) {
            console.log("请求失败", err);
        })
    }

    // 查询页面
    let searchMenu = document.getElementById("search-div");
    // 判断是否处于选中状态
    let flags = [false, false, false, false, false, false, false];
    // 搜索输入框
    let searchInputs = [];

    /**
     * 展示查询页面
     */
    function showSearchMen() {
        // 展示查询页面
        searchMenu.style.visibility = "visible";
        // 添加遮罩效果
        document.getElementById("overlay").style.visibility = "visible";

        let searchInputIds = ["search-number", "search-name", "search-college", "search-major", "search-grade"
            , "search-class", "search-age"];

        for (let i = 0; i < searchInputIds.length; i++) {
            let s = document.getElementById(searchInputIds[i]);
            searchInputs.push(s);
        }

        let searchCheckboxes = document.getElementsByClassName("check-search");

        for (let i = 0; i < searchCheckboxes.length; i++) {
            searchCheckboxes[i].onclick = function () {
                if (flags[i] === true) {
                    searchInputs[i].style.display = "";
                    flags[i] = false;
                } else {
                    searchInputs[i].style.display = "inline-block";
                    flags[i] = true;
                }
            }
        }

        let saveSearch = document.getElementById("save-search");
        saveSearch.onclick = function () {
            searchInformation();
        };

        let cancelSearch = document.getElementById("cancel-search");
        cancelSearch.onclick = function () {
            hiddenSearchMenu();
        };
    }

    /**
     * 隐藏查询界面
     */
    function hiddenSearchMenu() {
        //隐藏界面
        searchMenu.style.visibility = "";
        //取消遮罩效果
        document.getElementById("overlay").style.visibility = "";

        //使用jquery清空所有输入框
        $(".search-input").val("");

        // 隐藏所有的输入框，以及被选中的复选框
        let searchCheckboxes = document.getElementsByClassName("check-search");
        for (let i = 0; i < searchCheckboxes.length; i++) {
            searchCheckboxes[i].checked = false;
            if (flags[i] === true) {
                searchInputs[i].style.display = "";
                flags[i] = false;
            }
        }

    }

    /**
     * 查询学生信息
     *
     */
    function searchInformation() {
        //使用jquery获取输入信息
        let number = $("#search-number").val();
        let name = $("#search-name").val();
        let college = $("#search-college").val();
        let major = $("#search-major").val();
        let grade = $("#search-grade").val();
        let stuClass = $("#search-class").val();
        let age = $("#search-age").val();

        //以json形式存储
        let student = {
            number: number,
            name: name,
            college: college,
            major: major,
            grade: grade,
            stuClass: stuClass,
            age: age
        };

        // 隐藏查询
        hiddenSearchMenu();

        // 搜索到的学生
        let searchStudents = [];

        // 指定后台接口地址
        //let url = "http://localhost:8080/SearchStudent";
        let url = "http://121.36.58.0:8080/student/SearchStudent";

        // 发送请求
        Ajax(url, "post", student, function (res) {
            console.log("请求成功", res);
            //获取后台传回的数据
            let index = 0;
            // 获取后台传回的数据
            // 定义一个数组存放对象
            while (res[index] != null) {
                searchStudents.push(res[index]);
                index++;
            }

            setSearchTable(searchStudents);

        }, function (err) {
            console.log("请求失败", err);
        })
    }

    /**
     * 展示搜索结果页面
     *  注意，未考虑到多条需翻页
     * @param searchStudents 查询到的信息
     */
    let oldStudents = [];

    function setSearchTable(searchStudents) {
        //暂时存储起来，将students替换，为了防止其他的修改和删除功能能够正常进行
        if (tableType.length === 0) {
            oldStudents = students;
        }

        let oldSearchStudents = [];
        for (let i = 0; i < searchStudents.length; i++) {
            oldSearchStudents.push(searchStudents[i]);
        }

        students = searchStudents;

        insertToTable(searchStudents);

        //将表格类型变成searchTable
        tableType = "searchTable";

        //更换页脚信息
        document.getElementById("message").innerHTML =
            "第" + parseInt((searchStudents.length / count) + 1) + "页，共" + searchStudents.length + "条信息，每页显示" + count + "条";

        document.getElementById("foot-buttons").innerHTML =
            "<input type='button' value='返回' class='green-button footer-button' id='back'>";

        document.getElementById("back").onclick = function () {
            tableType = "";
            let flag = false;
            if (students.length < oldSearchStudents.length) {
                flag = true;
            } else {
                for (let i = 0; i < oldStudents.length; i++) {
                    for (let j = 0; j < students.length; j++) {
                        if (oldStudents[i]["id"] == students[j]["id"]) {
                            oldStudents[i] = students[j];
                        }
                    }
                }
            }
            if (flag) {
                getInformation(start, count);
            } else {
                //返回以后将原数组返回，为了能够在查询后修改信息
                students = oldStudents;

                insertToTable(students);
            }

            document.getElementById("foot-buttons").innerHTML =
                "<input type='button' class='green-button footer-button' value='上一页' id='previous'>" +
                "<input type='button' class='red-button footer-button' value='下一页' id='next'>";

            // 重新绑定事件
            // 上一页
            previous = document.getElementById("previous");
            previous.onclick = previousPage;

            // 下一页
            next = document.getElementById("next");
            next.onclick = nextPage;
        }
    }


};
// 表格
var studentTable = document.getElementById("student-table");

// 存储从数据库获取的学生信息
var students = [];

// 数据中的数据总条数
var sumStudents = 0;
getSumStudents();

// 增、改操作界面
var dealInformation = document.getElementById("deal-information");
// 保存类型，方便向后台提交数据
var saveType = null;

// 开始从数据库中获取的起始位置
var start = 0;
// 每次从数据库获取的个数
var count = 10;

// 上一页
var previous = null;
// 下一页
var next = null;

// 表格的类型，为了控制查询删除出现的问题
var tableType = "";


/**
 * 封装的一个Ajax请求函数
 * @param url 后台地址
 * @param type 传输类型
 * @param data 传输数据
 * @param success
 * @param error
 * @constructor
 */
function Ajax(url, type, data, success, error) {
    $.ajax({
        url: url,
        type: type,
        data: data,
        // async:false,
        dataType: "json",
        success: success,
        error: error
    })
}

/**
 * 获取数据库中的信息
 * @param start 开始位置
 * @param count 条数
 */
function getInformation(start, count) {
    /*// 数据中的数据总条数
    getSumStudents();*/

    // 以json形式存储
    let data = {
        start: start,
        count: count
    };

    // 指定后台接口地址
    //let url = "http://localhost:8080/GetStudents";
    let url = "http://121.36.58.0:8080/student/GetStudents";

    // 发送请求
    Ajax(url, "post", data, function (res) {
        //每次获取数据库的信息前将原表格清空
        students = [];

        // 获取后台传回的数据
        // 定义一个数组存放对象
        let index = 0;
        while (res[index] != null) {
            students.push(res[index]);
            index++;
        }

        // 获取后插入数据到表格
        insertToTable(students);

    }, function (err) {
        console.log("请求失败", err);

    })
}

/**
 * 获取当前数据库中的数据条数
 */
function getSumStudents() {

    // 指定后台接口地址
    // let url = "http://localhost:8080/GetSumStudents";
    let url = "http://121.36.58.0:8080/student/GetSumStudents";
    // 发送请求
    Ajax(url, "post", null, function (res) {
        sumStudents = res;

    }, function (err) {
        console.log("请求失败", err);

    })
}

/**
 * 向表格中填充数据
 */
function insertToTable(students) {
    //清除表格
    clearTable();
    // 更换页脚信息
    document.getElementById("message").innerHTML =
        "第" + (start / 10 + 1) + "页，共" + sumStudents + "条信息，每页显示" + count + "条";

    // 开始向表格插入数据
    let len = students.length;
    if (len != 0) {
        for (let i = 0; i < len; i++) {
            let student = students[i];
            let row = studentTable.insertRow();

            row.insertCell().innerHTML = "<input type='Checkbox' class='myCheck'/>";
            row.insertCell().innerText = i + 1;
            row.insertCell().innerText = student.number;
            row.insertCell().innerText = student.name;
            row.insertCell().innerText = student.college;
            row.insertCell().innerText = student.major;
            row.insertCell().innerText = student.grade;
            row.insertCell().innerText = student.stuClass;
            row.insertCell().innerText = student.age;
            row.insertCell().innerHTML = "<input type='button' value='修改' class='change' " +
                "style='width: 40px;border: none;border-radius: 10px;background-color:#70f2ff'>";

            if (i % 2 == 0) {
                row.style.backgroundColor = "#fff";
            }
        }

        // 当填充完成后才进行
        // 隔行鼠标移入变色
        changeTableRowColor();

    } else {
        alert("当前无数据！");
    }

}

/**
 * 鼠标移入表格变色
 */
function changeTableRowColor() {
    let tableRows = studentTable.getElementsByTagName("tr");
    for (let i = 1; i < tableRows.length; i++) {
        tableRows[i].onmouseover = function () {
            this.style.backgroundColor = "#a5e5aa";
        };

        if ((i + 1) % 2 == 1) {
            tableRows[i].onmouseout = function () {
                this.style.backgroundColor = "#eef1f8";
            }
        } else {
            tableRows[i].onmouseout = function () {
                this.style.backgroundColor = "#ffffff";
            }
        }
    }
}

/**
 * 清除table表中的数据
 */
function clearTable() {
    for (let i = studentTable.rows.length - 1; i >= 1; i--) {
        studentTable.deleteRow(i);
    }
}

/**
 * 上一页按钮事件
 */
function previousPage() {
    if (start == 0) {
        alert("没有上一页了！")
    } else {
        start -= count;
        getInformation(start, count);
    }
}

/**
 * 下一页按钮事件
 */
function nextPage() {
    if (students.length < count) {
        alert("没有下一页了！")
    } else if ((start + count) == sumStudents) {
        alert("没有下一页了！")
    } else {
        start += count;
        getInformation(start, count);
    }
}
