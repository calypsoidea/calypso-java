const { spawn } = require('child_process');
const path = require('path');

class JavaWrapper {

    constructor(_proxyDir, _className) {
        this.javaPath = path.resolve(__dirname, _proxyDir);
        this.className = _className;
        this.jarFile = this.javaPath + "/" + this.className + ".jar"
    }

    stdOutFunction(jsonOut) {
            // ??
    }

    stdErrFunction(jsonOut) {
            // ??
    }

    onClose(jsonOut) {

    }

    async executeServer(functionName, jsonArg) {
        // not tested - for multiple pushes and communications with the preocess
    return new Promise((resolve, reject) => {
        const javaProcess = spawn("java", ['-jar', this.jarFile, functionName, jsonArg]);
        
        const results = [];

        javaProcess.stdout.on('data', (data) => {
            const lines = data.toString().split('\n').filter(Boolean);
            lines.forEach(line => {
                // Process each line immediately
                console.log('Received:', line);
                results.push(line);
            });
        });

        javaProcess.on('close', (code) => {
            if (code === 0) {
                resolve(results);  // Resolve with all collected results
            } else {
                reject(new Error(`Process failed with code ${code}`));
            }
        });
    });
}

    async execute(functionName, jsonArg) {
    
    return new Promise((resolve, reject) => {

        const javaProcess = spawn("java", [
            '-jar', 
            this.jarFile,
            functionName,
            jsonArg
        ]);
        
        let output = '';
        let errorOutput = '';

        javaProcess.stdout.on('data', (data) => {
            output += data.toString();
            this.stdOutFunction(output)
        });

        javaProcess.stderr.on('data', (data) => {
            errorOutput += data.toString();
            this.stdErrFunction(errorOutput)
        });

        javaProcess.on('close', (code) => {
        if (code === 0) {
            resolve(output);
        } else {
            reject(new Error(`Java script exited with code ${code}: ${errorOutput}`));
        }

        }); 

    });
    
}

}

module.exports = { JavaWrapper }


