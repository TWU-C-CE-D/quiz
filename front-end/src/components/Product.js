import React, {Component} from 'react';
import {Form, Table, Button, Select, Input, DatePicker} from 'antd';
import '../styles/Product.css';

const FormItem = Form.Item;
const Option = Select.Option;
const {RangePicker} = DatePicker;

class Product extends Component {

    status = {
        submitButtonDisabled: false
    };

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            price: '',
            unit: '',
            url: ''
        };
    }

    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const {form} = this.props;
        form.validateFieldsAndScroll((err, values) => {
            if (!err) {
                this.status.submitButtonDisabled = true;
                fetch('http://localhost:8080/product', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(this.state)
                }).then(Product.checkStatus)
                    .then(result => {
                        this.status.submitButtonDisabled = false;
                        alert("添加成功");
                        form.resetFields();
                    })
                    .catch(result => {
                        this.status.submitButtonDisabled = false;
                        alert("商品名称已存在，请输入新的商品名称");
                        form.resetFields();
                    })
            }
        });
    };

    static checkStatus(response) {
        if (response.status >= 200 && response.status < 300) {
            return response;
        }
        const error = new Error(response.statusText);
        error.response = response;
        throw error;
    };

    render() {
        const {form} = this.props;
        const {getFieldDecorator} = form;

        return (
            <div className="product">
                <h1>添加商品</h1>
                <Form className="form-box" onSubmit={this.handleSubmit}>
                    <FormItem label="名称 :">
                        <div className="combination">
                            <div>{getFieldDecorator('name', {
                                rules: [{
                                    required: true,
                                    message: '名称不能为空'
                                }],
                            })(
                                <Input
                                    type='text'
                                    name='name'
                                    onChange={this.handleChange}
                                    value={this.state.name}
                                    placeholder='   名称'/>
                            )}</div>
                        </div>
                    </FormItem>

                    <FormItem label="价格 :">
                        <div className="combination">
                            <div>{getFieldDecorator('price',{
                                rules: [{
                                    required: true,
                                    message: '价格不能为空'
                                }, {
                                    message:'只能输入数字',
                                    pattern: /^[0-9]+$/
                                }],
                            })(
                                <Input
                                    type='text'
                                    name='price'
                                    onChange={this.handleChange}
                                    value={this.state.price}
                                    placeholder='   价格'/>
                            )}</div>
                        </div>
                    </FormItem>

                    <FormItem label="单位 :">
                        <div className="combination">
                            <div>{getFieldDecorator('unit',{
                                rules: [{
                                    required: true,
                                    message: '单位不能为空'
                                }],
                            })(
                                <Input
                                    type='text'
                                    name='unit'
                                    onChange={this.handleChange}
                                    value={this.state.unit}
                                    placeholder='   单位'/>
                            )}</div>
                        </div>
                    </FormItem>

                    <FormItem label="图片 :">
                        <div className="combination">
                            <div>{getFieldDecorator('url',{
                                rules: [{
                                    required: true,
                                    message: 'URL不能为空'
                                }],
                            })(
                                <Input
                                    type='text'
                                    name='url'
                                    onChange={this.handleChange}
                                    value={this.state.url}
                                    placeholder='   URL'/>
                            )}</div>
                        </div>
                    </FormItem>
                    <div className="operate">
                        <button
                            type="submit"
                            className="button primary"
                            disabled={this.status.submitButtonDisabled ? 'disabled' : ''}>提 交</button>
                    </div>
                </Form>
            </div>
        );
    }
}

export default Form.create()(Product)