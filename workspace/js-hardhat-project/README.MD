

# JS Hardhat Project - MEV Simulation

## Setup

1. Install dependencies
```bash
npm install

2. Set environment variables in .env

ALCHEMY_URL=https://eth-mainnet.alchemyapi.io/v2/your-key
TEST_PRIVATE_KEY=0x...

3. Compile Contracts

npm run compile

4. Run swap simulation

npm run run-swap

5.Run Tests

npm test

Run order

Mainnet Fork

1. Make sure your fork config is set (in hardhat.config.cjs):

hardhat: {
  forking: {
    url: process.env.ALCHEMY_MAINNET_URL || process.env.INFURA_MAINNET_URL,
    // blockNumber: 17900000, // optional pin
  },
},


2. Start a local node or just run scripts directly (Hardhat will spin a fork per run):

# either:
npx hardhat node --fork $ALCHEMY_MAINNET_URL

# and then (add --network localhost)
npx hardhat run scripts/manipulate-pool.cjs --network localhost

# or (one-off, spins fork per run):
npx hardhat run scripts/manipulate-pool.cjs --network hardhat
npx hardhat run scripts/arb-sim-uni-sushi.cjs --network hardhat
npx hardhat run scripts/scan-bot.cjs --network hardhat


