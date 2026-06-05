<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>FoodDelivery - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .hero { background: linear-gradient(135deg, #e74c3c, #c0392b); color: #fff; padding: 70px 20px; text-align: center; }
        .hero h1 { font-size: 2.8rem; margin-bottom: 12px; font-weight: 700; }
        .hero p  { font-size: 1.15rem; margin-bottom: 28px; opacity: 0.92; }
        .hero-search { display: flex; justify-content: center; gap: 10px; flex-wrap: wrap; }
        .hero-search input { padding: 12px 20px; border-radius: 30px; border: none; font-size: 1rem; width: 320px; outline: none; }
        .hero-search button { padding: 12px 28px; border-radius: 30px; border: none; background: #fff; color: #e74c3c; font-weight: 700; font-size: 1rem; cursor: pointer; }
        .scroll-section { padding: 40px 0 20px; background: #fff; }
        .scroll-section h2 { text-align: center; font-size: 1.8rem; color: #333; margin-bottom: 6px; }
        .scroll-section p  { text-align: center; color: #888; margin-bottom: 24px; font-size: 0.95rem; }
        .scroll-track-wrap { overflow: hidden; width: 100%; position: relative; }
        .scroll-track-wrap::before, .scroll-track-wrap::after { content: ''; position: absolute; top: 0; bottom: 0; width: 80px; z-index: 2; pointer-events: none; }
        .scroll-track-wrap::before { left: 0;  background: linear-gradient(to right, #fff, transparent); }
        .scroll-track-wrap::after  { right: 0; background: linear-gradient(to left,  #fff, transparent); }
        .scroll-track { display: flex; gap: 20px; padding: 10px 40px 20px; animation: scrollLeft 30s linear infinite; width: max-content; }
        .scroll-track:hover { animation-play-state: paused; }
        @keyframes scrollLeft { 0% { transform: translateX(0); } 100% { transform: translateX(-50%); } }
        .food-card { width: 200px; flex-shrink: 0; background: #fff; border-radius: 16px; box-shadow: 0 4px 16px rgba(0,0,0,0.10); overflow: hidden; transition: transform 0.2s; cursor: pointer; }
        .food-card:hover { transform: translateY(-6px); }
        .food-card img { width: 100%; height: 140px; object-fit: cover; display: block; }
        .food-card-body { padding: 12px; }
        .food-card-body h4 { font-size: 0.95rem; font-weight: 600; margin-bottom: 4px; color: #222; }
        .food-card-body p  { font-size: 0.8rem; color: #888; margin-bottom: 6px; }
        .food-card-body span { color: #e74c3c; font-weight: 700; font-size: 0.95rem; }
        .cuisine-section { padding: 50px 20px; background: #fff; }
        .cuisine-section h2 { text-align: center; font-size: 1.8rem; margin-bottom: 30px; color: #333; }
        .cuisine-grid { display: flex; gap: 16px; justify-content: center; flex-wrap: wrap; max-width: 900px; margin: 0 auto; }
        .cuisine-pill { background: #fff3f3; color: #e74c3c; border: 2px solid #e74c3c; border-radius: 30px; padding: 10px 24px; font-weight: 600; font-size: 0.95rem; cursor: pointer; text-decoration: none; transition: all 0.2s; }
        .cuisine-pill:hover { background: #e74c3c; color: #fff; }
        .features-section { background: #f9f9f9; padding: 50px 20px; }
        .features-section h2 { text-align: center; font-size: 1.8rem; margin-bottom: 30px; color: #333; }
        .features-grid { display: flex; gap: 20px; justify-content: center; flex-wrap: wrap; max-width: 900px; margin: 0 auto; }
        .feature-box { background: #fff; border-radius: 14px; padding: 30px 24px; text-align: center; width: 200px; box-shadow: 0 2px 12px rgba(0,0,0,0.07); }
        .feature-icon { font-size: 2.5rem; margin-bottom: 12px; }
        .feature-box h3 { font-size: 1rem; margin-bottom: 6px; color: #222; }
        .feature-box p  { font-size: 0.85rem; color: #888; }
        .cta-section { background: linear-gradient(135deg, #e74c3c, #c0392b); color: #fff; text-align: center; padding: 60px 20px; }
        .cta-section h2 { font-size: 2rem; margin-bottom: 12px; }
        .cta-section p  { font-size: 1rem; margin-bottom: 24px; opacity: 0.9; }
        .btn-white { background: #fff; color: #e74c3c; padding: 14px 36px; border-radius: 30px; font-weight: 700; font-size: 1rem; text-decoration: none; display: inline-block; }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/navbar.jsp" %>

<div class="hero">
    <h1>&#127374; Hungry? We've Got You!</h1>
    <p>Order delicious food from the best restaurants near you</p>
    <div class="hero-search">
        <form action="${pageContext.request.contextPath}/restaurant" method="get" style="display:flex;gap:10px;flex-wrap:wrap;justify-content:center">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" placeholder="Search restaurants or cuisines...">
            <button type="submit">Search</button>
        </form>
    </div>
</div>

<div class="scroll-section">
    <h2>Popular Dishes</h2>
    
    <div class="scroll-track-wrap">
        <div class="scroll-track">
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=1" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=400&h=280&fit=crop" alt="Butter Chicken">
                <div class="food-card-body"><h4>Butter Chicken</h4><p>Spice Garden</p><span>&#8377;220</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=2" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400&h=280&fit=crop" alt="Margherita Pizza">
                <div class="food-card-body"><h4>Margherita Pizza</h4><p>Pizza Planet</p><span>&#8377;250</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=4" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&h=280&fit=crop" alt="Cheese Burger">
                <div class="food-card-body"><h4>Cheese Burger</h4><p>Burger Barn</p><span>&#8377;220</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=3" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1512058564366-18510be2db19?w=400&h=280&fit=crop" alt="Fried Rice">
                <div class="food-card-body"><h4>Fried Rice</h4><p>Dragon Wok</p><span>&#8377;160</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=1" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=400&h=280&fit=crop" alt="Paneer Tikka">
                <div class="food-card-body"><h4>Paneer Tikka</h4><p>Spice Garden</p><span>&#8377;180</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=2" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=400&h=280&fit=crop" alt="Pasta">
                <div class="food-card-body"><h4>Pasta Arrabiata</h4><p>Pizza Planet</p><span>&#8377;180</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=3" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1606491956689-2ea866880c84?w=400&h=280&fit=crop" alt="Chicken Manchurian">
                <div class="food-card-body"><h4>Chicken Manchurian</h4><p>Dragon Wok</p><span>&#8377;210</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=4" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1576777647209-e8733d7b851d?w=400&h=280&fit=crop" alt="French Fries">
                <div class="food-card-body"><h4>French Fries</h4><p>Burger Barn</p><span>&#8377;80</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=1" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=400&h=280&fit=crop" alt="Dal Makhani">
                <div class="food-card-body"><h4>Dal Makhani</h4><p>Spice Garden</p><span>&#8377;150</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=3" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=400&h=280&fit=crop" alt="Spring Rolls">
                <div class="food-card-body"><h4>Spring Rolls</h4><p>Dragon Wok</p><span>&#8377;100</span></div>
            </a>
            <%-- Duplicate cards below for seamless loop --%>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=1" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=400&h=280&fit=crop" alt="Butter Chicken">
                <div class="food-card-body"><h4>Butter Chicken</h4><p>Spice Garden</p><span>&#8377;220</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=2" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400&h=280&fit=crop" alt="Margherita Pizza">
                <div class="food-card-body"><h4>Margherita Pizza</h4><p>Pizza Planet</p><span>&#8377;250</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=4" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&h=280&fit=crop" alt="Cheese Burger">
                <div class="food-card-body"><h4>Cheese Burger</h4><p>Burger Barn</p><span>&#8377;220</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=3" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1512058564366-18510be2db19?w=400&h=280&fit=crop" alt="Fried Rice">
                <div class="food-card-body"><h4>Fried Rice</h4><p>Dragon Wok</p><span>&#8377;160</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=1" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=400&h=280&fit=crop" alt="Paneer Tikka">
                <div class="food-card-body"><h4>Paneer Tikka</h4><p>Spice Garden</p><span>&#8377;180</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=3" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1606491956689-2ea866880c84?w=400&h=280&fit=crop" alt="Chicken Manchurian">
                <div class="food-card-body"><h4>Chicken Manchurian</h4><p>Dragon Wok</p><span>&#8377;210</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=4" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1576777647209-e8733d7b851d?w=400&h=280&fit=crop" alt="French Fries">
                <div class="food-card-body"><h4>French Fries</h4><p>Burger Barn</p><span>&#8377;80</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=1" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=400&h=280&fit=crop" alt="Dal Makhani">
                <div class="food-card-body"><h4>Dal Makhani</h4><p>Spice Garden</p><span>&#8377;150</span></div>
            </a>
            <a href="${pageContext.request.contextPath}/restaurant?action=menu&id=3" class="food-card" style="text-decoration:none">
                <img src="https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=400&h=280&fit=crop" alt="Spring Rolls">
                <div class="food-card-body"><h4>Spring Rolls</h4><p>Dragon Wok</p><span>&#8377;100</span></div>
            </a>
        </div>
    </div>
</div>

<div class="cuisine-section">
    <h2>Browse by Cuisine</h2>
    <div class="cuisine-grid">
        <a href="${pageContext.request.contextPath}/restaurant?action=search&keyword=Indian"   class="cuisine-pill">Indian</a>
        <a href="${pageContext.request.contextPath}/restaurant?action=search&keyword=Italian"  class="cuisine-pill">Italian</a>
        <a href="${pageContext.request.contextPath}/restaurant?action=search&keyword=Chinese"  class="cuisine-pill">Chinese</a>
        <a href="${pageContext.request.contextPath}/restaurant?action=search&keyword=American" class="cuisine-pill">American</a>
        <a href="${pageContext.request.contextPath}/restaurant?action=list"                    class="cuisine-pill">All</a>
    </div>
</div>

<div class="features-section">
    <h2>Why Choose Us?</h2>
    <div class="features-grid">
        <div class="feature-box"><div class="feature-icon">&#127978;</div><h3>Top Restaurants</h3><p>Handpicked restaurants with best ratings</p></div>
        <div class="feature-box"><div class="feature-icon">&#9889;</div><h3>Fast Delivery</h3><p>Hot food at your doorstep quickly</p></div>
        <div class="feature-box"><div class="feature-icon">&#128179;</div><h3>Easy Payment</h3><p>Simple and secure checkout</p></div>
        <div class="feature-box"><div class="feature-icon">&#128241;</div><h3>Live Tracking</h3><p>Track your order in real time</p></div>
    </div>
</div>

<div class="cta-section">
    <h2>Ready to Order?</h2>
    <p>Browse hundreds of dishes from top restaurants near you</p>
    <a href="${pageContext.request.contextPath}/restaurant?action=list" class="btn-white">Order Now</a>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>