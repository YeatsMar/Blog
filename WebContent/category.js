function edit(id, oldName) {
    var newName = prompt("新的分类名称？");
    if(newName===null)return;
    var xhr = new XMLHttpRequest();
    xhr.open("post", "Category", false);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("id=" + id + "&oldName=" + encodeURIComponent(oldName) + "&newName=" + encodeURIComponent(newName) + "&type=edit");

    if (!(xhr.getResponseHeader("Fail") === null)) {
        alert("重名,请重新命名");
    } else if (!(xhr.getResponseHeader("Success") === null)) {
        alert("修改成功");
        location.reload();
    } else {
        alert("未知错误");
    }
}

function del(id, name) {
    if (confirm("确认删除“" + name + "”分类？")) {
        var xhr = new XMLHttpRequest();
        xhr.open("post", "Category", false);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send("id=" + id + "&name=" + encodeURIComponent(name) + "&type=del");

        if (!(xhr.getResponseHeader("Success") === null)) {
            alert("删除成功");
            location.reload();
        } else {
            alert("未知错误");
        }
    }
}

function cannot(){
    alert("不能执行此操作，请确保该分类中无文章");
}
