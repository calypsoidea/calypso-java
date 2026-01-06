
const { JavaWrapper } = require('../veronica/JavaWrapper.cjs')

class HelloWorldWrapper extends JavaWrapper {

    constructor () {
        
        super("javatests", "helloworlds.HelloWorld")
    }

     onProcess(jsonOut) {
        console.log('Hello World, I am Carlos, I am here on Java')
        console.log(jsonOut.toString());
    }

}

module.exports = { HelloWorldWrapper }

async function main() {

    const yus = new HelloWorldWrapper()
    const arg = 'Kadu New Coin'

    yus.execute(arg)

} 

if (require.main === module) {
    main().catch(console.error);
}
