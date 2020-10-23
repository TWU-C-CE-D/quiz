import React, {Component} from 'react';
import '../styles/Order.css';

const URL = 'http://localhost:8080/orders';

class Order extends Component {

    status = {
        deleteButtonDisabled: false
    };

    constructor(props) {
        super(props);
        this.state = {
            data: {},
        };
    }

    componentDidMount() {
        fetch(URL).then((response) => response.json())
            .then((result) => {
                this.setState({
                    data: result.getOrderVOs
                });
            });
    }

    handler(id) {
        this.status.deleteButtonDisabled = true;
        fetch('http://localhost:8080/order/' + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(result => {
                alert("删除成功");
                this.status.deleteButtonDisabled = false;
                fetch(URL).then((response) => response.json())
                    .then((result) => {
                        console.log(result);
                        this.setState({
                            data: result.getOrderVOs
                        });
                    });
            })
            .catch(result => {
                alert("订单删除失败，请稍后再试");
                console.log(result);
                this.status.deleteButtonDisabled = false;
            })
    }

    render() {
        return (
            <div className="order">
                <table>
                    {Object.keys(this.state.data)
                        .map((key) => (
                            <tbody key={key}>
                                <tr>
                                    <td>
                                        <h4>订单号：{this.state.data[key].orderId}</h4>
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td>
                                        <button
                                            className="btn btn-success m-2"
                                            onClick={() => this.handler(this.state.data[key].orderId)}
                                            disabled={this.status.deleteButtonDisabled ? 'disabled' : ''}
                                        >删除
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <h4>#</h4>
                                    </td>
                                    <td>
                                        <h4>名称</h4>
                                    </td>
                                    <td>
                                        <h4>单价</h4>
                                    </td>
                                    <td>
                                        <h4>数量</h4>
                                    </td>
                                </tr>
                                {Object.keys(this.state.data[key].getProductVOs)
                                    .map((index) => (
                                        <tr key={index}>
                                            <td>
                                                <h4>{this.state.data[key].getProductVOs[index].sortId}</h4>
                                            </td>
                                            <td>
                                                <h4>{this.state.data[key].getProductVOs[index].name}</h4>
                                            </td>
                                            <td>
                                                <h4>{this.state.data[key].getProductVOs[index].price}</h4>
                                            </td>
                                            <td>
                                                <h4>{this.state.data[key].getProductVOs[index].num}</h4>
                                            </td>
                                        </tr>
                                    ))}
                                    <tr>
                                        <td><h4>总价</h4></td>
                                        <td></td>
                                        <td><h4>{this.state.data[key].total}</h4></td>
                                        <td></td>
                                    </tr>
                            </tbody>
                        ))}
                </table>
            </div>
        );
    }
}

export default Order;