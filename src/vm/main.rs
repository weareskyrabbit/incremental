use std::fs::File;
use std::io::Read;
use std::panic::resume_unwind;

fn main() -> Result<(), Box<std::error::Error>> {
    let mut file = File::open("hello.wc")?;
    let mut buf = Vec::new();
    let _ = file.read_to_end(&mut buf)?;

    // read .wc file layout
    let vm = VirtualMachine { input : buf, position : 0 };

    let mut instructions = Vec::new();

    let magic = read_int(&mut buf);
    if magic != 0xdeadbeaf {
        println!("magic is invalid");
    }
    let functions_size = read_int(&mut buf);
    let instructions_size = read_int(&mut buf);
    let mut counter1;
    counter1 = 0;
    while counter1 < instructions_size {
        instructions_size.push(read_int(&mut buf));
        counter1 = counter1 + 1;
    }
    let constant_pool_size = read_int(&mut buf);
    counter1 = 0;
    while counter1 < constant_pool_size {
        instructions_size.push(read_int(&mut buf));
        counter1 = counter1 + 1;
    }

    println!("{:?}", buf);
    Ok(())
}

struct VirtualMachine {
    input : Vec<u8>,
    position : i32
}
impl VirtualMachine {
    fn read_int(&self) -> i32 {
        let int = self.input.get(self.position) << 24 |
            self.input.get(self.position + 1) << 16 |
            self.input.get(self.position + 2) <<  8 |
            self.input.get(self.position + 3);
        /* self.position = self.position + 4; */
        int
    }
    fn execute(&self) {
        // TODO
    }
}