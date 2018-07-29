# ETH币系群发(红包)工具

### How to use
#### 安装Java环境(略)
#### 执行发送脚本
`java -jar red.jar config.json `

### Configuration
```
{
	"coin": "币种名称, 例 ETH, MEX ...",
	"contractAddress": "Tokon币的合约地址, ETH不需要此项.",
	"mnemonic": "你自己的中文助记词(目前仅适配中文), 记得用空格隔开",
	"commonGasPrice": 8000000000,
	"commonGasLimit": 60000,
	"commonNums": 100, //默认发送数量
	"commonUnit": "ether", //默认发送精度
	"list": [
		{
			"name": "收币人名字(optional)",
			"toAddress": "0xdbb5226db2e0512b00d9efac390262c231c7c134",
			"nums": 180, //(optional) 指定发送数量
			"unit": "ether" //(optional) 指定发送精度
		},
		{
			"name": "yuming",
			"toAddress": "0x4ac1edd535e26513dd8a0a26fdff1fb4ee114b62",
			"gasPrice": 7000000000, //(optional) 特殊指定矿工费
			"gasLimit": 60000 //(optional) 特殊指定矿工费
		}
	],
	"logLevel": "debug",	//(optional) log打印开关,
	"logPath": "./logred.txt" //(optional) log保存路径名.
}
```

### 注意
由于ETH网络结点有时会不稳定, 连接不上ETH网络, 出现超时异常, 此是需要重启一次即可.

log日志是追加的.
