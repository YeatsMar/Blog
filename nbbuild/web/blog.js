//like
function like(isLike, userId, articleId) {
	var like = document.getElementById("like");// component to use its
												// innerHTML

	// AJAX
	var xhr = new XMLHttpRequest();
	xhr.open("post", "Blog", false);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("isLike=" + encodeURIComponent(isLike) + "&userId=" + userId
			+ "&articleId=" + articleId);// encodeURIComponent(String)
	// wait the response of servlet
	var status = xhr.getResponseHeader("LikeStatus");
	if (status == "like") {
            like.innerHTML = "<a class=\"help\" onclick=\"like(<%=isLike %>, <%=id %>, <%=articleId %>)\" id=\"like\" href=\"\" alt=\"\"><%=isLikeStr %>(<%=LikeService.getLikeNumber(articleIdStr) %>)</a>";
	} else {
		like.innerHTML = "赞";// 操作失败
	}
}

function cannot() {
	alert("未登录用户无法点赞！");
}

function serialize(form) {// serialize an element: name=value

	var parts = [], field = null, i, len;// parts is an array, field is an
											// object, synchronously declare
											// these 4 variable
	for (i = 0, len = form.elements.length; i < len; i++) {
		field = form.elements[i];
		if (field.name.length) {// if the element of the form has
								// "name"attribute
			parts.push(encodeURIComponent(field.name) + "="
					+ encodeURIComponent(field.value));
		}
	}
	return parts.join("&");// return a String instead of as Array
}

function addComment(articleId, userId) {
	//alert(articleId+userId);//right
	//AJAX
	var xhr=new XMLHttpRequest();
    xhr.open("post","Blog",false);
    xhr.setRequestHeader("addComment","true");
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    var form = document.getElementById("comment");
    xhr.send("articleId=" + articleId + "&userId=" + userId + "&" + serialize(form));
    //wait the response of servlet
    var status=xhr.getResponseHeader("AddCommentSuccess");
    if(!(status===null)) {
        alert("评论成功");
        location.reload(true);//刷新
    }
    return false;
}

function deleteComment(id) {
	if(confirm("要删除咯！")){//right
	var form = document.getElementById("comment");
	var xhr=new XMLHttpRequest();
    xhr.open("post","Blog",false);
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhr.setRequestHeader("deleteComment","true");
    xhr.send("id=" + id);
    //wait the response of servlet
        }
}

function deleteArticle(id) {
	if(confirm("要删除咯！")){//right
	var form = document.getElementById("comment");
	var xhr=new XMLHttpRequest();
    xhr.open("post","Blog",false);
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhr.setRequestHeader("deleteArticle","true");
    xhr.send("articleId=" + id);
    //wait the response of servlet
    window.location.href = "blog_list.jsp?category=all"; 
        }
}