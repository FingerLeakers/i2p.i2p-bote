<%--
 Copyright (C) 2009  HungryHobo@mail.i2p
 
 The GPG fingerprint for HungryHobo@mail.i2p is:
 6DD3 EAA2 9990 29BC 4AD2 7486 1E2C 7B61 76DC DC12
 
 This file is part of I2P-Bote.
 I2P-Bote is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 I2P-Bote is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with I2P-Bote.  If not, see <http://www.gnu.org/licenses/>.
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ib" uri="I2pBoteTags" %>

<ib:message key="Settings" var="title" scope="request"/>
<jsp:include page="header.jsp"/>

<div class="main">
    <h2>
        <ib:message key="Settings"/>
    </h2>
    <br/>
    
    <jsp:useBean id="jspHelperBean" class="i2p.bote.web.JSPHelper"/>
    <c:set var="configuration" value="${jspHelperBean.configuration}"/>
    <c:if test="${param.action eq 'save'}">
        <jsp:setProperty name="configuration" property="autoMailCheckEnabled" value="${param.autoMailCheckEnabled eq 'on' ? 'true' : 'false'}"/>
        <jsp:setProperty name="configuration" property="mailCheckInterval" value="${param.mailCheckInterval}"/>
        <ib:saveConfiguration/>
    </c:if>
    
    <form action="settings.jsp" method="post">
        <input type="hidden" name="action" value="save"/>
        
        <c:set var="autoMailCheckEnabled" value="${jspHelperBean.configuration.autoMailCheckEnabled}"/>
        <c:set var="checked" value="${autoMailCheckEnabled ? ' checked' : ''}"/>
        <input type="checkbox"${checked} name="autoMailCheckEnabled"/>Check for mail every
        <input type="text" name="mailCheckInterval" size="3" value="${configuration.mailCheckInterval}"/> minutes
        
        <p/>
        <button type="submit"><ib:message key="Save"/></button>
    </form> 
</div>

<jsp:include page="footer.jsp"/>