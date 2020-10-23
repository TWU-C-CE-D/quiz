import React, {Component} from 'react';
import imgURL from '../img/cola.jpg'
import '../styles/Shop.css';
import shopImg from "../img/shop.png";
import Modal from "antd/es/modal";
import Button from "antd/es/button";

const URL = 'http://localhost:8080/products';
const customStyles = {
    content : {
        top                   : '50%',
        left                  : '50%',
        right                 : 'auto',
        bottom                : 'auto',
        marginRight           : '-50%',
        transform             : 'translate(-50%, -50%)'
    }
};

class Shop extends Component {

    status = {
        addButtonDisabled: false
    };

    showModal = () => {
        this.setState({
            visible: true
        })
    };

    onCancel = () => {
        this.setState({
            visible: false
        })
    };

    onOk = () => {
        this.setState({
            visible: false
        })
    };

    constructor(props) {
        super(props);
        this.state = {
            data: {}
        };
    }

    componentDidMount() {
        fetch(URL).then((response) => response.json())
            .then((result) => {
                this.setState({
                    data: result
                });
            });
    }

    handler(key) {
        console.log("id: " + this.state.data[key].id);
        this.state.data[key].number = 1;
        this.status.addButtonDisabled = true;
        fetch('http://localhost:8080/order/' + this.state.data[key].id, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.state.data[key])
        }).then(response => response.json())
            .then(result => {
                alert("添加成功");
                console.log(result);
                this.status.addButtonDisabled = false;
            })
            .catch(result => {
                console.log(result);
                this.status.addButtonDisabled = false;
            })
    }

    render() {
        const { visible } = this.state;
        return (
            <div className="shop">
                {Object.keys(this.state.data)
                    .map((key) => (
                        <div key={key} style={{
                            border: '2px solid #EEEEEE',
                            width: '150px',
                            height: '240px',
                            margin: '30px',
                            float: 'left'
                        }}>
                            <img src={this.state.data[key].url} alt="cola"/>
                            <div className="label-content">
                                <h4>{this.state.data[key].name}</h4>
                                <p>单价：{this.state.data[key].price}/{this.state.data[key].unit}</p>
                            </div>
                            <button
                                className="btn btn-success m-2"
                                style={{width:'30px',height:'30px',borderRadius:'50%',border:'2px solid #EEEEEE',backgroundColor:'white',float:'right',marginRight:'17px'}}
                                onClick={() => this.handler(key)}
                                disabled={this.status.addButtonDisabled ? 'disabled' : ''}
                            >+
                            </button>
                        </div>
                    ))}
                <div className="cart">
                    <button
                        style={{width:'30px',height:'30px',borderRadius:'50%',border:'none',backgroundColor:'rgb(0, 122, 255)',float:'right',
                            marginTop: '32%', marginRight: '5%'}}
                        onClick={this.showModal}
                    ><img src={shopImg} alt='shop'/>
                    </button>
                </div>
                <div>
                    <Modal
                        visible={visible}
                        onCancel={this.onCancel}
                        onOk={this.onOk}
                        style={customStyles}
                        contentLabel="Example Modal">
                        <div>
                            Modal Content
                        </div>
                    </Modal>
                </div>
            </div>
        );
    }
}

export default Shop;