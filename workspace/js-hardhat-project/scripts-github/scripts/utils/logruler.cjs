class LogRuler {
    constructor(_precision) {
        
        this.XWithLogX = [];
        this.logAnd10ByX = [];

        this.precision = _precision;
        
    }
    
    initializeScales() {
        // Typical slide rule range: 1 to 10 (log 0 to log 1)

       /* for (let logX = 0; logX <= 1; logX += 0.001) {
            const X = Math.pow(10, logX);
            
            this.C.push({
                logValue: logX,
                actualValue: X,
                position: logX * 100 // cm position
            });
            
            this.CI.push({
                logValue: -logX,           // log(1/X) = -log(X)
                actualValue: 1 / X,
                position: (1 - logX) * 100 // Inverted position
            });

            */

    }

    valueOut(_r0, _r1,_valueIn) {

    }

    printRuler() {
        // Typical slide rule range: 1 to 10 (log 0 to log 1)

        const increment = Math.pow(10, (this.precision * - 1))
        const indexMultiplier = Math.pow(10, this.precision)

        for (let logX = 0; logX <= 1; /*logX += 0.001*/ logX += increment) {
            const X = Math.pow(10, logX);
            
            const invX = (10 / X )

            const index  = logX * indexMultiplier;

            // console.log(` | ${logX.toFixed(this.precision)} | ${X.toFixed(this.precision)} | ${invX.toFixed(this.precision)} |`)

            console.log(` | ${index.toFixed(0)} | ${X.toFixed(this.precision)} | ${invX.toFixed(this.precision)} |`)
           
           
        }
    }

    initializeXWithLogX() {

        const increment = Math.pow(10, (this.precision * (-1)))

        for (let i = 0; i <= 9; i += increment) {
          
            let X = ( i + 1).toFixed(this.precision)

             this.XWithLogX[i] = Math.log10(X) ;

             console.log(` | ${X} | ${this.XWithLogX[i]}`)

           
        }

    }


    initialize10byX() {

        const increment = Math.pow(10, (this.precision * - 1))
        
        for (let X = 1; X <= 10; X += increment) {
          


            
           
           
        }
    }
}
    

module.exports = { LogRuler}

async function main() {

    const ruler = new LogRuler(2)

    ruler.initializeXWithLogX()

}

if (require.main === module) {
    main().catch(console.error);
}