<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix = "rms" uri = "/WEB-INF/link.tld"%>

<main class="mdl-layout__content">
	<div class="mdl-card mdl-shadow--6dp">
		<div class="mdl-card__title mdl-color--primary mdl-color-text--white">
			<h2 class="mdl-card__title-text">Acme Co.</h2>
		</div>
		<form action="save" method="POST">
  			<div class="mdl-card__supporting-text">
				<div class="mdl-textfield mdl-js-textfield">
					<input type="hidden" name="id" value="${user.getId()}"/>
					<input class="mdl-textfield__input" type="text" id="username"  name="username" value="${user.getUserName()}"/>
					<label class="mdl-textfield__label" for="username">Username</label>
				</div>
				<div class="mdl-textfield mdl-js-textfield">
					<input class="mdl-textfield__input" type="password" id="userpass" name="userpass" value="${user.getPassword()}" />
					<label class="mdl-textfield__label" for="userpass">Password</label>
				</div>
			</div>
			<div class="mdl-card__actions mdl-card--border">
				<button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Save</button>
			</div>
		</form>
	</div>
</main>

