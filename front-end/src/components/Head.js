import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';
import '../styles/Head.css';
import shopImg from '../img/shop.png';
import orderImg from '../img/order.png';
import productImg from '../img/product.png';

class Head extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="head">
                <div><NavLink to='/shop' style={{textDecoration: 'none'}} activeStyle={{backgroundColor: 'rgb(23, 145, 255)'}}><img src={shopImg} alt='shop'/>  商城</NavLink></div>
                <div><NavLink to='/order' style={{textDecoration: 'none'}} activeStyle={{backgroundColor: 'rgb(23, 145, 255)'}}><img src={orderImg} alt='order'/>  订单</NavLink></div>
                <div><NavLink to='/product' style={{textDecoration: 'none'}} activeStyle={{backgroundColor: 'rgb(23, 145, 255)'}}><img src={productImg} alt='product'/>  添加商品</NavLink></div>
            </div>
        );
    }
}

export default Head;