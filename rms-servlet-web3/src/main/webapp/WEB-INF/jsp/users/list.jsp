<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix = "rms" uri = "/WEB-INF/link.tld"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<div style="margin-bottom:15px;">
	<a href="add" class="mdl-button mdl-button--raised mdl-js-button mdl-js-ripple-effect mdl-button--primary">Add</a>
	<a href="javascript:;" onclick="pageScript.delete()" class="mdl-button mdl-button--raised mdl-js-button mdl-js-ripple-effect mdl-button--accent">Delete</a>
</div>
<table id="myTable" class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp">
	<thead>
    	<tr>
        	<th class="mdl-data-table__cell--non-numeric">User Name</th>
            <th>Password</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
    	<c:forEach items = "${users}" var="user">
      		<tr>
        		<td data-id="${user.id}" class="mdl-data-table__cell--non-numeric"><c:out value = "${user.userName}"/></td>
       			<td><c:out value = "${user.password}"/></td>
       			<td>
       				<a href="edit?id=<c:out value = "${user.id}"/>">Edit</a>
       			</td>
       		</tr>
  		</c:forEach>
  	</tbody>
</table>
<script>
	var pageScript = {
		delete: function(){
			var id = [];
			$("input.mdl-checkbox__input").each(function(i){
				if($(this).is(":checked") && i!=0){
					id.push( $("#myTable tr:eq("+i+") td:eq(1)").data("id") );
				}
			});
			$.ajax({
				url		: "delete",
				type	: "POST",
				dataType: "json",
				data	: {id: JSON.stringify(id)},
				success: function(result){
		        	console.log({response:result});
		        	location.reload();
		    	}
			});
			
		}		
	}
	
</script>