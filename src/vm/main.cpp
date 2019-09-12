#include <iostream>
#include <fstream>
#include <stack>
#include <vector>
#include <string>
#include <algorithm>
#include <iterator>

using namespace std;

class VirtualMachine {
private:
    vector<uint8_t> input;
    int position;
    int* instructions;
    string** constant_pool;
    int counter; // program counter
    stack<int> stack; // runtime stack
public:
    VirtualMachine(const vector<uint8_t> input) {
        this->input = input;
        this->position = 0;
        this->counter = 0;
    }
    void analyze() {
        int magic = read_int();
        if (magic != 0xdeadbeef) {
            cout << "error : magic is invalid" << endl;
        }
        int functions_size = read_int();
        int instructions_size = read_int();
        instructions = new int[instructions_size];
        for (int i = 0; i < instructions_size; i++) {
            instructions[i] = read_int();
        }
        int constant_pool_size = read_int();
        constant_pool = new string*[constant_pool_size];
        for (int i = 0; i < constant_pool_size; i++) {
            constant_pool[i] = new string(20, ' ');
        }
        for (int i = 0; i < constant_pool_size; i++) {
            int size = read_int();
            vector<uint8_t> bytes = read(size);
            for (int j = 0; j < size; j++) {
                /* constant_pool[i][j] = bytes[j]; */
                cout << bytes[j];
            }
        }
    }
    void execute() {
        while (true) {
            int left = 0;
            int right = 0;
            switch (type()) {
                case 0x00: // stop
                    return;
                case 0x10: // push
                    stack.push(operand1());
                    break;
                case 0x20: // add
                    left = stack.top();
                    stack.pop();
                    right = stack.top();
                    stack.pop();
                    stack.push(left + right);
                    break;
                case 0x21: // sub
                    left = stack.top();
                    stack.pop();
                    right = stack.top();
                    stack.pop();
                    stack.push(left - right);
                    break;
                case 0x22: // mul
                    left = stack.top();
                    stack.pop();
                    right = stack.top();
                    stack.pop();
                    stack.push(left * right);
                    break;
                case 0x23: // div
                    left = stack.top();
                    stack.pop();
                    right = stack.top();
                    stack.pop();
                    stack.push(left / right);
                    break;
                case 0x30: // print
                    cout << constant_pool[operand1()];
                    break;
            }
            counter++;
        }
    }
private:
    vector<uint8_t> read(const int length) {
        vector<uint8_t> bytes;
        bytes.reserve(length);
        for (int i = 0; i < length; i++) {
            bytes.push_back(input[position + i]);
        }
        position += length;
        return bytes;
    }
    unsigned int read_int() {
        vector<uint8_t> bytes = read(4);
        return bytes[0] << 24 |
                bytes[1] << 16 |
                bytes[2] << 8 |
                bytes[3];
    }
    unsigned int type() {
        return instructions[counter] >> 24 & 0xff;
    }
    unsigned int operand1() {
        return instructions[counter] >> 16 & 0xff;
    }
};
int main() {
    vector<uint8_t> input;

    // read
    ifstream fin(R"(C:\Users\mirro\Desktop\myprojects\incremental\hello.wc)", ios::in | ios::binary);
    if (!fin) {
        cout << "error : file 'hello.wc' didn't open" << endl;
        return 1;
    }
    while (!fin.eof()) {
        uint8_t byte = 0;
        fin.read((char*) &byte, sizeof(uint8_t));
        input.push_back(byte);
    }
    VirtualMachine* vm = new VirtualMachine(input);

    // analyze
    vm->analyze();

    // execute
    /* vm->execute(); */
    return 0;
}