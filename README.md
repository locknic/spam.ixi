# SPAM.IXI
This is a simple IOTA Ict IXI built to send a random transaction with a configurable interval. The purpose of this module is not to stress the network, but just to simulate some fake activity on idle nodes.

## Installation

This guide assumes you have an Ict up and running. Please find instructions on [iotaledger/ict](https://github.com/iotaledger/ict#installation).

1. Under the web gui of your Ict node, go to your "manage modules" tab
2. Click "install third party module" button
3. Type in `locknic/spam.ixi`
4. Click install

To stop spamming, simply remove the module 

Note: You can also build this module yourself as with any other

## Configuration

`INTERVAL_MS`: The millisecond interval between each spam transaction. (e.g. `1000` = 1 spam transaction a second)

Note: In order to specify an interval lower than 1000, you must add the `f` flag to your configured interval. (e.g. `500f`)
