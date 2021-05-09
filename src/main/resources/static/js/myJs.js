/**
 *  修改
 *  使用put请求，
 *  @param data 异步提交数据
 */
function change(data, url, ...is) {
    $.ajax({
        //请求方式
        type : "put",
        //请求的媒体类型
        // contentType: "application/json;charset=UTF-8",
        //请求地址
        url : url,
        //数据，json字符串
        data : data,
        //请求成功
        success : function(result) {
            if(result.data === "true" || result.data === true) {
                if(result === true || result === "true") {
                    layer.alert("增加成功", {
                        icon: 6
                    })
                } else {
                    layer.alert("增加失败", {
                        icon: 3
                    })
                }
            }
        },
        //请求失败，包含具体的错误信息
        error : function(e){
            console.log(e.status);
            console.log(e.responseText);
        }
    });

    if(is.length === 0) {
        init();
    }
}


function save(data, url) {
    $.ajax({
        //请求方式
        type : "post",
        //请求的媒体类型
        // contentType: "application/json;charset=UTF-8",
        //请求地址
        url : url,
        //数据，json字符串
        data : data,
        //请求成功
        success : function(result) {
            if(result === true) {
                layer.alert("增加成功", {
                        icon: 6
                    }, function() {
                        //关闭当前frame
                        xadmin.close();

                        // 可以对父窗口进行刷新
                        xadmin.father_reload();
                });
            } else {
                layer.alert("增加失败", {
                    icon: 6
                });
            }

        },
        //请求失败，包含具体的错误信息
        error : function(e){
            console.log(e.status);
            console.log(e.responseText);
        }
    });
}