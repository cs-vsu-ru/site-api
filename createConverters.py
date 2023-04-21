import os

entity_path = "src/main/java/cs/vsu/is/domain"
dto_path = "src/main/java/cs/vsu/is/service/dto"
dto_list = []
entity_list = []
for root, dirs, files in os.walk(entity_path):
    for filename in files:
        entity_list.append(filename.split(".")[0])
for root, dirs, files in os.walk(dto_path):
    for filename in files:
        dto_list.append(filename.split("DTO")[0])
for dto in dto_list:
    for entity in entity_list:
        if entity.find(dto) != -1:
            src = 'src/main/java/cs/vsu/is/service/convertor/RoleConverter.java'
            dest = 'src/main/java/cs/vsu/is/service/convertor/'+dto+'Converter.java'
            with open(src, 'r') as f:
                data = f.read()
            data = data.replace('Role', entity)
            data = data.replace('RoleConverter', dto+'Converter')
            data = data.replace('RoleDTO', dto+'DTO')
            with open(dest, 'w') as f:
                f.write(data)
