import React, {Component} from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import Head from "./components/Head";
import Foot from "./components/Foot";
import Shop from "./components/Shop";
import Order from "./components/Order";
import Product from "./components/Product";
import './styles/App.css';

class App extends Component {
    render() {
        return (
            <BrowserRouter>
                <Head/>
                <div className="body">
                    <div className="app">
                        <Switch>
                            <Route path='/shop' component={Shop}/>
                            <Route path='/order' component={Order}/>
                            <Route path='/product' component={Product}/>
                        </Switch>
                    </div>
                </div>
                <Foot/>
            </BrowserRouter>
        );
    }
}

export default App;
